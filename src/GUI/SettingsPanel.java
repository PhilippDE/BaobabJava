package GUI;

import Data.Settings;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    SettingsPanel(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SettingsPanel.this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                SettingsPanel.this.setContentPane(SettingsPanel.this.rootPanel);
                SettingsPanel.this.setMinimumSize(new Dimension(GraphicsConstants.settingsPanelFULLHDX, GraphicsConstants.settingsPanelFULLHDY));
                SettingsPanel.this.setPreferredSize(new Dimension(GraphicsConstants.settingsPanelFULLHDX, GraphicsConstants.settingsPanelFULLHDY));
                SettingsPanel.this.setSize(new Dimension(GraphicsConstants.settingsPanelFULLHDX, GraphicsConstants.settingsPanelFULLHDY));
                SettingsPanel.this.setMaximumSize(new Dimension(GraphicsConstants.settingsPanelFULLHDX, GraphicsConstants.settingsPanelFULLHDY));
                SettingsPanel.this.setTitle("Settings");
                SettingsPanel.this.pack();
                SettingsPanel.this.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        rootPanel=new JPanel();

        contentPanel=new JPanel();
        contentPanel.setFont(GraphicsConstants.standardFont);

        multithreadingCheck=new JCheckBox();
        multithreadingCheck.setFont(GraphicsConstants.standardFont);
        multithreadingCheck.setSelected(Settings.multiThreading);

        //Label for threadcount textfield
        threadcountLabel=new JLabel();
        threadcountLabel.setFont(GraphicsConstants.standardFont);

        //Numberformatter needed so user only can input numbers
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(true);
        formatter.setCommitsOnValidEdit(true);

        //Textfield showing the maximum amount of threads available. The user can change this be editing the text
        threadCountLimit=new JFormattedTextField(formatter);
        threadCountLimit.setValue(Settings.threadCountLimit);

        //Button for applying changes
        applyButton=new JButton();
        applyButton.setFont(GraphicsConstants.standardFont);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.multiThreading = multithreadingCheck.isSelected();
                Settings.threadCountLimit = (Integer) threadCountLimit.getValue();
                SettingsPanel.this.dispose();
            }
        });

        //Cancel button; when clicked window closes and settings are not changed
        cancelButton=new JButton();
        cancelButton.setFont(GraphicsConstants.standardFont);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsPanel.this.dispose();
            }
        });
    }
}
