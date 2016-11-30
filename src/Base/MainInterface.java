package Base;

import Classes.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Matrix on 23-Nov-16.
 * MainInterface
 * Builds the main interaction interface and handles file building
 */
public class MainInterface extends JFrame {
    static final long serialVersionUID = 23390000;
    private String fileName;

    private JTree worldTree;

    private JMenuBar menuBar;

    private JTextArea textArea;

    private JButton startJobsButton;
    private JButton stopJobsButton;

    private JTable jobsTable;

    private World world;

    // Main Constructor
    private MainInterface() throws IllegalStateException {
        world = new World();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
    }

    // GUI Initialization
    private boolean initGUI() {
        // Initializing User Interface
        setTitle("SeaPorts World");

        // Setting width = 60%, height = 80% (of current screen size)
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int startWidth = device.getDisplayMode().getWidth();
        int startHeight = device.getDisplayMode().getHeight();

        setSize((int) (startWidth * 0.6), (int) (startHeight * 0.8));
        // Set center x,y to screen center x,y
        setLocationRelativeTo(null);

        initMenuBar();
        initTree();
        initJobDisplay();
        initUIElements();

        validate();

        // Returning isValid so we can throw if something goes wrong
        return isValid();
    }
    private void initMenuBar() {

        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem openFile = new JMenuItem("Open File...");
        openFile.addActionListener((ActionEvent e) -> {
            try {
                loadFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        fileMenu.add(openFile);

        JMenuItem displayFile = new JMenuItem("Display Data File");
        displayFile.addActionListener((ActionEvent e) -> {
            handleDisplayFile();
        });
        fileMenu.add(displayFile);

        menuBar.add(fileMenu);


        JMenu jobMenu = new JMenu("Jobs");

        JMenuItem start = new JMenuItem("Begin Jobs");
        start.addActionListener((ActionEvent e) -> {
            world.startJobs();
        });
        jobMenu.add(start);

        JMenuItem pause = new JMenuItem("Pause Jobs");
        pause.addActionListener((ActionEvent e) -> {
            // TODO:
            // Implement a method to pause all jobs
        });
        jobMenu.add(pause);

        JMenuItem stop  = new JMenuItem("Stop Jobs");
        stop.addActionListener((ActionEvent e) -> {
            world.stopJobs();
        });
        jobMenu.add(stop);

        menuBar.add(jobMenu);

        setJMenuBar(menuBar);
    }
    private void initTree() {
        DefaultMutableTreeNode baseNode = new DefaultMutableTreeNode("Tree Empty");
        worldTree = new JTree(baseNode);

        JScrollPane treeScroll = new JScrollPane(worldTree);
        treeScroll.setPreferredSize(new Dimension((int) (this.getWidth() * 0.3), this.getHeight()));
        worldTree.setRootVisible(false);
        worldTree.setShowsRootHandles(true);
        add(treeScroll, BorderLayout.WEST);
    }
    private void initJobDisplay() {
        jobsTable = new JTable();

        String[] columnNames = {"Job", "Ship/Dock", "Total Time", "Progress"};
        Object[][] data = {
                {
                        "TEST.0x001", "TEST.SHIP.01 - DOCK1", new Integer(50),
                        new DefaultTableCellRenderer().getTableCellRendererComponent(jobsTable, new JProgressBar(0, 100), false, false, 0, 3)
                },
                {
                        "TEST.0x002", "TEST.SHIP.02 - DOCK1", new Integer(50), new JProgressBar()
                }
        };

        jobsTable.setModel(new DefaultTableModel(data, columnNames));

        JScrollPane tableScroll = new JScrollPane(jobsTable);
        tableScroll.setPreferredSize(new Dimension(this.getWidth(), (int) (this.getHeight() * 0.15)));

        jobsTable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textArea.setText(e.getSource().toString());
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        add(tableScroll, BorderLayout.SOUTH);
    }
    private void initUIElements() {
        // Init Text Area
        textArea = new JTextArea();
        textArea.setText("Please select a file to open!");
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    // File Actions
    private void loadFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser(".");
        int result = fileChooser.showOpenDialog(this);

        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileName = selectedFile.getAbsolutePath();
            world.processFile(selectedFile);
        }
        else if(result == JFileChooser.CANCEL_OPTION) { System.out.println("File selection was canceled"); }
        else { throw new IOException("File selection was invalid"); }

        JTree newTree = world.getTree();
        worldTree.setModel(newTree.getModel());
        worldTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                String path = e.getPath().getLastPathComponent().toString();
                Integer index = Integer.parseInt(path.split(":")[0]);
                handleTreeSelection(index);
            }
        });

        textArea.setText(String.format("%s", world));
    }

    // Listeners
    private void handleTreeSelection(Integer i) throws NoSuchElementException {
        BaseObject o = world.findObject(i);
        if(o == null) { throw new NoSuchElementException(String.format("Object at index %d was not found!", i)); }
        textArea.setText(String.format("%s", o));
    }
    private void handleDisplayFile() {
        textArea.setText("");
        try {
            Scanner sc = new Scanner(new File(fileName));
            while(sc.hasNextLine()) {
                textArea.append(sc.nextLine());
            }
        } catch (IOException e) {
            textArea.setText("There was an error processing the file...");
            e.printStackTrace();
        }
    }

    // MAIN
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainInterface main = new MainInterface();
            main.setVisible(true);
        });
    }
}