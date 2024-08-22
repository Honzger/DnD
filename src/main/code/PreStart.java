package main.code;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PreStart {
	private static int Tilesize = 80;
    private static final String JSON_FILE_NAME = "C:/Users/Mainuser/eclipse-workspace/DnD/src/JSOON/Items.json";  // Path to the JSON file
    private static final String DIRECTORY_PATH = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/Items/";  // Path to the directory to scan

    public PreStart() {
        JSONArray jsonArray = readJsonFile(JSON_FILE_NAME);
        File folder = new File(DIRECTORY_PATH);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Directory does not exist or is not a directory.");
            return;
        }

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.out.println("The directory is empty or an error occurred.");
            return;
        }

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (!isFileInJsonArray(jsonArray, fileName)) {
                    addFileNameToJsonArray(jsonArray, fileName);
                }
            }
        }

        writeJsonFile(JSON_FILE_NAME, jsonArray);
    }

    private static JSONArray readJsonFile(String fileName) {
        JSONArray jsonArray = new JSONArray();
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            jsonArray = new JSONArray(content);
        } catch (IOException e) {
            // File doesn't exist or cannot be read, continue with empty JSONArray
            System.out.println("No existing data file found. Starting with an empty array.");
        } catch (org.json.JSONException e) {
            // JSON parsing error, continue with empty JSONArray
            System.out.println("Existing data file is corrupted. Starting with an empty array.");
        }
        return jsonArray;
    }

    private static void writeJsonFile(String fileName, JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(jsonArray.toString(4)); // Indent with 4 spaces for readability
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isFileInJsonArray(JSONArray jsonArray, String fileName) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("string").equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    private static void addFileNameToJsonArray(JSONArray jsonArray, String fileName) {
        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(480, 480));

        // Load the initial image
        String imagePath = DIRECTORY_PATH + File.separator + fileName;
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(Tilesize, Tilesize, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, Tilesize, Tilesize);


        // Load the background image
        ImageIcon tileIcon = new ImageIcon("C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/backgroundInv.jpg");
        Image imaaage = tileIcon.getImage().getScaledInstance(Tilesize, Tilesize, Image.SCALE_SMOOTH);
        tileIcon = new ImageIcon(imaaage);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                JLabel tileLabel = new JLabel(tileIcon); // Create a new label for each tile
                tileLabel.setBounds(Tilesize * i, Tilesize * j, Tilesize, Tilesize);
                layeredPane.add(tileLabel, JLayeredPane.DEFAULT_LAYER);
            }
        }


        // Add the background and image labels to the layered pane
        layeredPane.add(imageLabel, JLayeredPane.PALETTE_LAYER);

        // Create input fields for user to enter integers
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel fileLabel = new JLabel("File: ");
        JLabel fileNameLabel = new JLabel(fileName);

        JLabel firstIntLabel = new JLabel("First Int: ");
        JTextField firstIntField = new JTextField();

        JLabel secondIntLabel = new JLabel("Second Int: ");
        JTextField secondIntField = new JTextField();

        inputPanel.add(fileLabel);
        inputPanel.add(fileNameLabel);
        inputPanel.add(firstIntLabel);
        inputPanel.add(firstIntField);
        inputPanel.add(secondIntLabel);
        inputPanel.add(secondIntField);

        // Create a panel to hold both the layered pane and input panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(layeredPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        int previousFirstInt = -1;
        int previousSecondInt = -1;

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, mainPanel,
                    "Enter details for " + fileName, JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int firstInt = Integer.parseInt(firstIntField.getText());
                    int secondInt = Integer.parseInt(secondIntField.getText());

                    // Resize the image based on user input
                    ImageIcon newImageIcon = new ImageIcon(imagePath);
                    Image newImage = newImageIcon.getImage().getScaledInstance(80 * firstInt, 80 * secondInt, Image.SCALE_SMOOTH);
                    newImageIcon = new ImageIcon(newImage);
                    imageLabel.setIcon(newImageIcon);
                    imageLabel.setBounds(0, 0, newImageIcon.getIconWidth(), newImageIcon.getIconHeight());

                    // Revalidate the layered pane to ensure proper rendering
                    layeredPane.revalidate();
                    layeredPane.repaint();

                    // Save the values if they match the previous confirmation
                    if (firstInt == previousFirstInt && secondInt == previousSecondInt) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("string", fileName);
                        jsonObject.put("firstInt", firstInt);
                        jsonObject.put("secondInt", secondInt);

                        jsonArray.put(jsonObject);
                        break;
                    } else {
                        previousFirstInt = firstInt;
                        previousSecondInt = secondInt;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter valid integers for the sizes.");
                }
            } else {
                break;
            }
        }
    }
}
