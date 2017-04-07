package GUI;

import Data.Settings;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Settings panel.
 * All settings the user can change should be editable only in here
 * Created by Marcel on 07.04.2017.
 */
public class SettingsPanel extends JFrame{

    private JPanel rootPanel;
    private JCheckBox multithreadingCheck;
    private JPanel contentPanel;
    private JFormattedTextField threadCountLimit;
    private JLabel threadcountLabel;
    private JButton applyButton;
    private JButton cancelButton;

    public SettingsPanel(){
        EventQueue.invokeLater(() -> {
            SettingsPanel.this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            SettingsPanel.this.setContentPane(SettingsPanel.this.rootPanel);
            SettingsPanel.this.setMinimumSize(new Dimension(GraphicsConstants.settingsPanelFULLHDX, GraphicsConstants.settingsPanelFULLHDY));
            SettingsPanel.this.setPreferredSize(new Dimension(GraphicsConstants.settingsPanelFULLHDX, GraphicsConstants.settingsPanelFULLHDY));
            SettingsPanel.this.setSize(new Dimension(GraphicsConstants.settingsPanelFULLHDX, GraphicsConstants.settingsPanelFULLHDY));
            SettingsPanel.this.setMaximumSize(new Dimension(GraphicsConstants.settingsPanelFULLHDX, GraphicsConstants.settingsPanelFULLHDY));
            SettingsPanel.this.setTitle("Settings");
            SettingsPanel.this.pack();
            SettingsPanel.this.setVisible(true);
        });
    }

    private void createUIComponents() {
        rootPanel=new JPanel();

        contentPanel=new JPanel();

        multithreadingCheck=new JCheckBox();
        multithreadingCheck.setFont(GraphicsConstants.standardFont);
        multithreadingCheck.setSelected(Settings.multiThreading);

        threadcountLabel=new JLabel();
        threadcountLabel.setFont(GraphicsConstants.standardFont);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(true);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);

        threadCountLimit=new JFormattedTextField(formatter);
        threadCountLimit.setValue(Settings.threadCountLimit);

        applyButton=new JButton();
        applyButton.setFont(GraphicsConstants.standardFont);
        applyButton.addActionListener(e->{
            Settings.multiThreading=multithreadingCheck.isSelected();
            Settings.threadCountLimit=(Integer)threadCountLimit.getValue();
            this.dispose();
        });

        cancelButton=new JButton();
        cancelButton.setFont(GraphicsConstants.standardFont);
        cancelButton.addActionListener(e->{
            this.dispose();
        });
    }
}
