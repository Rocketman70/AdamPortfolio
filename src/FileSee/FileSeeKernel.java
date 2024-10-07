package FileSee;
import java.io.File;

import components.sequence.Sequence1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/*
 * Some notes about the usage of this program: Intended to pass valid file paths
 * to programs using this implementation as a utility.
 *
 * Designed for use with GNU/Linux but may work due to lack of hard-coded file
 * path structures.
 */

/**
 * COMPATIBILITY ALERT: COMPATIBLE WITH STANDARD GNU/LINUX SYSTEMS
 * {@code fileSee} implemented to visually represent a file tree to be used as a
 * visual interface for a user to select files on Linux systems.
 *
 * @convention [All strings that are a member of $this.dir are regular, case
 *             sensitive chars, will not contain '/' or '\' or sequences invalid
 *             for filesystem nav.]
 *
 * @correspondence this = [if this.dir = "" then it is the file system root else
 *                 it is root + selected files.]
 *
 * @author Adam Abbott
 */
public final class FileSeeKernel {

    /*
     * Private members----------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String dir;

    /**
     * Current list of files in selected dir.
     */
    private Sequence1L<File> currentFiles;

    /**
     * Creator of initial representation using user's home directory key.
     */
    private void createNewRep() {
        this.dir = System.getProperty("user.home");
        this.currentFiles = new Sequence1L<>();
    }
    /*
     * Constructors-------------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public FileSeeKernel() {
        this.createNewRep();
    }

    /**
     * Constructor from {@code String}.
     *
     * @param mandir
     *            {@code String} to initialize from.
     */
    public FileSeeKernel(String mandir) {
        assert mandir != null : "Violation of directory != null";
        this.dir = mandir;
        this.currentFiles = new Sequence1L<>();

    }
    /*
     * Kernel methods-----------------------------------------------------------
     */

    /**
     * Displays a numbered list of files and directories in working directory.
     */
    public void displayNumberedFileTree() {
        this.listFiles();
        System.out.println("Current directory: " + this.currentDir());
        System.out.println("0. [Parent Directory]");

        for (int i = 0; i < this.currentFiles.length(); i++) {
            File file = this.currentFiles.entry(i);
            String prefix = file.isDirectory() ? "[D] " : "[F] ";
            System.out.println((i + 1) + ". " + prefix + file.getName());
        }
    }

    /**
     * Creates new list of files.
     */
    public void listFiles() {
        this.currentFiles.clear();
        File directory = new File(this.dir);
        File[] files = directory.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                this.currentFiles.add(i, files[i]);
            }
        }
    }
    /**
     * When the user selects a file, this method will return the name of the file.
     * 
     * @param index
     *      Index of file to select. 
     * @return
     *    Returns the name of the file selected.
     */
    public String selectFile(int index) {
        String fileName = this.currentFiles.entry(index).getName();
        return fileName;
    }

    /**
     *
     * @return this.dir Returns the current working directory.
     */
    public String currentDir() {
        return this.dir;
    }

    /**
     * @args Command-line arguments to demonstrate.
     */
    public static void main(String[] args) {
        System.out.println("Demonstration of the FileSee class.");
        FileSeeKernel fileSee = new FileSeeKernel();

        SimpleWriter out = new SimpleWriter1L();

        fileSee.displayNumberedFileTree();

        out.println("List of files/folders in current directory");
        fileSee.listFiles();
        out.println(fileSee.currentFiles.toString());

        out.println("Returns current directory");
        out.println(fileSee.currentDir());

        out.close();
    }
}
