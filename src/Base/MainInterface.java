package Base;

import Classes.*;
import org.jetbrains.annotations.TestOnly;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
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

    private World world;

    // Main Constructor
    public MainInterface() throws IllegalStateException {
        world = new World();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boolean didLoad = initGUI();

        //if(!didLoad) { throw new IllegalStateException("Could not init GUI"); }

        return;
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
    private void initUIElements() {
        // Init Text Area
        textArea = new JTextArea();
        textArea.setText("Please select a file to open!");
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Init Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        startJobsButton = new JButton("Start Jobs");
        startJobsButton.addActionListener((ActionEvent e) -> {
            world.startJobs();
        });

        stopJobsButton = new JButton("Stop Jobs");
        stopJobsButton.addActionListener((ActionEvent e) -> {
            // TODO: finish method
        });
        panel.add(startJobsButton);
        panel.add(stopJobsButton);

        add(panel, BorderLayout.SOUTH);

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
            return;
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
        return;
    }
}