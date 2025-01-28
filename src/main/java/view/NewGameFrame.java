package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class NewGameFrame extends JFrame {
    DesignModel designModel = new DesignModel();

    private ImageIcon[] characterIcons = {new ImageIcon("./bomberblitz/src/assets/characters/testPic1.png"), new ImageIcon("./bomberblitz/src/assets/characters/testPic2.png"), new ImageIcon("./bomberblitz/src/assets/characters/testPic3.png"), new ImageIcon("./bomberblitz/src/assets/characters/testPic4.png")};
    private String[] characterTexts = {"Character 1\n\nTwice as fast!", "Character 2\n\nFaster bomb recharge!", "Character 3\n\nBigger explosion range!", "Character 4\n\nQuicker bomb detonation!"};
    private int player1Character = 0;
    private int player2Character = 0;

    private String player1Name = "";
    private String player2Name = "";

    private String player1controls = "WASD";
    private String player2controls = "Arrow Keys";                                                      //fix later
    private int roundsNum = 0;
    private int mapNum = 0;


    public NewGameFrame() {
        super("BomberBlitz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(designModel.bgcolor);

        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgImage = new ImageIcon("./bomberblitz/src/assets/bg2.png");
                g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(new BorderLayout());
        this.setContentPane(bgPanel);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        JLabel title = new JLabel("BomberBlitz");
        title.setFont(designModel.font.deriveFont(Font.BOLD, 100f));
        title.setForeground(designModel.textColor);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(title, BorderLayout.CENTER);

        JButton backButton = createActionButton("Back");
        backButton.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        backButton.addActionListener(e -> {
            new MenuFrame();
            this.dispose();
        });
        titlePanel.add(backButton, BorderLayout.WEST);

        Dimension backButtonSize = backButton.getPreferredSize();
        Box.Filler filler = new Box.Filler(backButtonSize, backButtonSize, backButtonSize);
        titlePanel.add(filler, BorderLayout.EAST);

        bgPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel playerPanels = new JPanel();
        playerPanels.setLayout(new BorderLayout(0,20));
        playerPanels.setOpaque(false);
        playerPanels.setBackground(designModel.primaryColor);
        playerPanels.setAlignmentY(Component.CENTER_ALIGNMENT);
        playerPanels.setPreferredSize(new Dimension(800, 800));
        bgPanel.add(playerPanels, BorderLayout.LINE_START);

        JPanel player1Container = new JPanel(new FlowLayout());
        player1Container.setOpaque(false);
        JPanel player1Panel = createPlayerPanel(1);
        player1Container.add(player1Panel);
        playerPanels.add(player1Container, BorderLayout.NORTH);

        JPanel player2Container = new JPanel(new FlowLayout());
        player2Container.setOpaque(false);
        JPanel player2Panel = createPlayerPanel(2);
        player2Container.add(player2Panel);
        playerPanels.add(player2Container, BorderLayout.SOUTH);

        JPanel mapContainer = new JPanel(new FlowLayout());
        mapContainer.setOpaque(false);
        JPanel mapPanel = createMapPanel();
        mapContainer.add(mapPanel);
        bgPanel.add(mapContainer, BorderLayout.LINE_END);

        JPanel startButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        startButtonPanel.setOpaque(false);
        startButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0,30, 30));
        JButton startButton = createActionButton("Start Game");
        startButtonPanel.add(startButton);
        bgPanel.add(startButtonPanel, BorderLayout.SOUTH);
        startButton.addActionListener(e -> {
            //start game
            this.dispose();
            new GameFrame();

        });


        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JButton createActionButton(String txt){
        JButton button = new JButton(txt);
        button.setFont(designModel.font.deriveFont(Font.BOLD, 50f));
        button.setForeground(designModel.textColor);
        button.setBackground(designModel.primaryColor);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 7, 10));
        return button;
    }
    public JButton createArrowButton(String txt){
        JButton button = new JButton(txt);
        button.setFont(designModel.font.deriveFont(Font.BOLD, 50f));
        button.setForeground(designModel.textColor);
        button.setBackground(new Color(0,0,0,0));
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return button;
    }
    public void updateCharacterText(JTextArea textArea, int playerID){
        if(playerID == 1){
            textArea.setText(characterTexts[player1Character]);
        } else if (playerID == 2){
            textArea.setText(characterTexts[player2Character]);
        }
    }
    public JTextArea createTextArea(String txt){
        JTextArea textArea = new JTextArea(){
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(new Color(59, 24, 95, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        textArea.setText(txt);
        textArea.setFont(designModel.font.deriveFont(30f));
        textArea.setForeground(designModel.textColor);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 4, 4, designModel.primaryColor),
                        BorderFactory.createMatteBorder(4, 4, 0, 0, designModel.darkAccentColor)
                ),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea.setAlignmentY(Component.CENTER_ALIGNMENT);
        textArea.setOpaque(false);
        textArea.setFocusable(false);
        textArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                textArea.setFocusable(true);
                textArea.requestFocus();
            }
        });
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(txt)) {
                    textArea.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText(txt);
                }
            }
        });
        return textArea;
    }

    public JPanel createCharacterPanel( int playerID, JPanel playerDataPanel){
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BorderLayout(20,0));
        characterPanel.setOpaque(false);

        JLabel characterImage = new JLabel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = null;
                if(playerID == 1){
                    img = characterIcons[player1Character].getImage();;
                } else if (playerID == 2){
                    img = characterIcons[player2Character].getImage();
                }

                double aspectRatio = (double) img.getWidth(null) / img.getHeight(null);

                int height = getHeight();
                int width = (int) (height * aspectRatio);

                if (width > getWidth()) {
                    width = getWidth();
                    height = (int) (width / aspectRatio);
                }

                g.drawImage(img, 0, 0, width, height, this);
            }
        };
        characterImage.setPreferredSize(new Dimension(256, 256));
        characterImage.setHorizontalAlignment(SwingConstants.CENTER);
        characterImage.setVerticalAlignment(SwingConstants.CENTER);
        characterImage.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 5, designModel.darkAccentColor));
        characterPanel.add(characterImage, BorderLayout.CENTER);

        JButton leftArrow = createArrowButton("<");
        leftArrow.addActionListener(e -> {
            if (playerID == 1) {
                if(player1Character == 0){
                    player1Character = characterIcons.length - 1;
                } else {
                    player1Character--;
                }
            } else if (playerID == 2){
                if(player2Character == 0){
                    player2Character = characterIcons.length - 1;
                } else {
                    player2Character--;
                }
            }
            characterImage.repaint();
            updateCharacterText((JTextArea) playerDataPanel.getComponent(1), playerID);
            playerDataPanel.repaint();
        });
        characterPanel.add(leftArrow, BorderLayout.LINE_START);


        JButton rightArrow = createArrowButton(">");
        rightArrow.addActionListener(e -> {
            if (playerID == 1) {
                if(player1Character == characterIcons.length - 1){
                    player1Character = 0;
                } else {
                    player1Character++;
                }
            } else if (playerID == 2){
                if(player2Character == characterIcons.length - 1){
                    player2Character = 0;
                } else {
                    player2Character++;
                }
            }
            characterImage.repaint();
            updateCharacterText((JTextArea) playerDataPanel.getComponent(1), playerID);
            playerDataPanel.repaint();
        });
        characterPanel.add(rightArrow, BorderLayout.LINE_END);

        return characterPanel;
    }

    public JPanel createButtonLabel(String txt){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setPreferredSize(new Dimension(80, 80));

        JLabel label = new JLabel(txt);
        label.setFont(designModel.font.deriveFont(20f));
        label.setForeground(designModel.textColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        JTextArea keyText = new JTextArea();
        keyText.setText("Key");
        keyText.setFont(designModel.font.deriveFont(22f));
        keyText.setForeground(designModel.textColor);
        keyText.setBackground(designModel.secondaryColor);
        keyText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 4, 4, designModel.primaryColor),
                        BorderFactory.createMatteBorder(4, 4, 0, 0, designModel.darkAccentColor)
                ),
                BorderFactory.createEmptyBorder(10, 15, 0, 15)
        ));
        keyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        keyText.setAlignmentY(Component.CENTER_ALIGNMENT);
        keyText.setOpaque(false);
        keyText.setFocusable(false);
        keyText.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                keyText.setFocusable(true);
                keyText.requestFocus();
            }
        });
        keyText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (keyText.getText().equals("Key")) {
                    keyText.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (keyText.getText().isEmpty()) {
                    keyText.setText("Key");
                }
            }
        });
        panel.add(keyText);

        return panel;
    }

    public JDialog popupSettingsMenu(int playerID){
        JDialog settingsDialog = new JDialog(this, "Control Settings", true);
        settingsDialog.setLayout(new BorderLayout());
        settingsDialog.setPreferredSize(new Dimension(500, 300));
        settingsDialog.setUndecorated(true);
        settingsDialog.setResizable(false);
        settingsDialog.setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(designModel.darkAccentColor);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(designModel.bgcolor);
                g.fillRect(5, 5, getWidth() - 10, getHeight() - 10);
            }
        });

        JLabel settingsLabel = new JLabel("Control Settings");
        settingsLabel.setFont(designModel.font.deriveFont(30f));
        settingsLabel.setForeground(designModel.textColor);
        settingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        settingsLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        settingsDialog.add(settingsLabel, BorderLayout.NORTH);

        JPanel firstRow = new JPanel();
        firstRow.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        firstRow.setOpaque(false);
        settingsDialog.add(firstRow, BorderLayout.CENTER);

        JPanel secondRow = new JPanel();
        secondRow.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        secondRow.setOpaque(false);
        settingsDialog.add(secondRow, BorderLayout.CENTER);

        JPanel up = createButtonLabel("Up");
        JPanel down = createButtonLabel("Down");
        JPanel left = createButtonLabel("Left");
        JPanel right = createButtonLabel("Right");
        JPanel bomb = createButtonLabel("Bomb");
        JPanel barrier = createButtonLabel("Barrier");
        firstRow.add(up);
        firstRow.add(down);
        firstRow.add(left);
        firstRow.add(right);
        secondRow.add(bomb);
        secondRow.add(barrier);

        JButton saveButton = new JButton("OK");
        saveButton.setFont(designModel.font.deriveFont(30f));
        saveButton.setForeground(designModel.textColor);
        saveButton.setBackground(designModel.secondaryColor);
        saveButton.setOpaque(false);
        saveButton.setBorderPainted(false);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        saveButton.addActionListener(e -> {
            if(playerID == 1){
                player1controls = ((JTextArea)up.getComponent(1)).getText() + ((JTextArea)down.getComponent(1)).getText() + ((JTextArea)left.getComponent(1)).getText() + ((JTextArea)right.getComponent(1)).getText() + ((JTextArea)bomb.getComponent(1)).getText() + ((JTextArea)barrier.getComponent(1)).getText();
                System.out.printf("Player 1 controls: %s\n", player1controls);
            } else if (playerID == 2){
                player2controls = ((JTextArea)up.getComponent(1)).getText() + ((JTextArea)down.getComponent(1)).getText() + ((JTextArea)left.getComponent(1)).getText() + ((JTextArea)right.getComponent(1)).getText() + ((JTextArea)bomb.getComponent(1)).getText() + ((JTextArea)barrier.getComponent(1)).getText();
                System.out.printf("Player 2 controls: %s\n", player2controls);
            }
            settingsDialog.dispose();
        });
        settingsDialog.add(saveButton, BorderLayout.SOUTH);



        return settingsDialog;
    }

    public JPanel dataPanel(int playerID){
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout(0, 20));
        dataPanel.setOpaque(false);

        JPanel firstLineWrapper = new JPanel();
        firstLineWrapper.setLayout(new BoxLayout(firstLineWrapper, BoxLayout.X_AXIS));
        firstLineWrapper.setOpaque(false);
        dataPanel.add(firstLineWrapper, BorderLayout.NORTH);

        JTextArea name = createTextArea("Player Name");

        if(playerID==1 && !Objects.equals(name.getText(), "Player Name")){
            player1Name = name.getText();
        }
        if(playerID==2 && !Objects.equals(name.getText(), "Player Name")){
            player2Name = name.getText();
        }
        firstLineWrapper.add(name);

        JButton settingsButton = new JButton("S");
        settingsButton.setFont(designModel.font.deriveFont(30f));
        settingsButton.setForeground(designModel.bgcolor);
        settingsButton.setBackground(designModel.secondaryColor);
        settingsButton.setOpaque(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        settingsButton.addActionListener(e -> {
            JDialog popup = popupSettingsMenu(playerID);
            popup.pack();
            Point location = settingsButton.getLocationOnScreen();
            popup.setLocation(location);
            popup.setVisible(true);
        });
        firstLineWrapper.add(settingsButton);

        JTextArea characterText = new JTextArea();
        updateCharacterText(characterText, playerID);
        characterText.setFont(designModel.font.deriveFont(Font.BOLD,30f));
        characterText.setForeground(designModel.bgcolor);
        characterText.setAlignmentX(SwingConstants.CENTER);
        characterText.setOpaque(false);
        characterText.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        characterText.setLineWrap(true);
        characterText.setWrapStyleWord(true);
        characterText.setEditable(false);
        dataPanel.add(characterText, BorderLayout.CENTER);


        return dataPanel;
    }

    public JPanel createMapSelectPanel(){
        JPanel mapSelectPanel = new JPanel();
        mapSelectPanel.setLayout(new BorderLayout(20,0));
        mapSelectPanel.setOpaque(false);
        mapSelectPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        JLabel mapImage = new JLabel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = null;
                img = characterIcons[mapNum].getImage();

                double aspectRatio = (double) img.getWidth(null) / img.getHeight(null);

                int height = getHeight();
                int width = (int) (height * aspectRatio);

                if (width > getWidth()) {
                    width = getWidth();
                    height = (int) (width / aspectRatio);
                }

                g.drawImage(img, 0, 0, width, height, this);
            }
        };
        mapImage.setPreferredSize(new Dimension(256, 256));
        mapImage.setHorizontalAlignment(SwingConstants.CENTER);
        mapImage.setVerticalAlignment(SwingConstants.CENTER);
        mapImage.setBorder(BorderFactory.createMatteBorder(0, 0, 7, 7, designModel.darkAccentColor));
        mapSelectPanel.add(mapImage, BorderLayout.CENTER);

        JButton leftArrow = createArrowButton("<");
        leftArrow.addActionListener(e -> {
            if(mapNum == 0){
                mapNum = characterIcons.length - 1;
            } else {
                mapNum--;
            }
            mapImage.repaint();
        });
        mapSelectPanel.add(leftArrow, BorderLayout.LINE_START);


        JButton rightArrow = createArrowButton(">");
        rightArrow.addActionListener(e -> {
            if(mapNum == characterIcons.length - 1){
                mapNum = 0;
            } else {
                mapNum++;
            }
            mapImage.repaint();
        });
        mapSelectPanel.add(rightArrow, BorderLayout.LINE_END);

        return mapSelectPanel;
    }

    public JPanel createPlayerPanel(int playerID){
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout(30,30));
        playerPanel.setOpaque(false);
        playerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel playerDataPanel = dataPanel(playerID);
        playerPanel.add(playerDataPanel, BorderLayout.LINE_END);

        JPanel playerCharacterPanel = createCharacterPanel(playerID, playerDataPanel);
        playerPanel.add(playerCharacterPanel, BorderLayout.LINE_START);


        return playerPanel;
    }


    public JPanel createMapPanel(){
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());
        mapPanel.setOpaque(false);
        mapPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JTextArea roundsText = createTextArea("Rounds");
        roundsText.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(roundsText);

        JPanel mapSelectPanel = createMapSelectPanel();
        centerPanel.add(mapSelectPanel);

        mapPanel.add(centerPanel, BorderLayout.NORTH);

        return mapPanel;
    }
}
