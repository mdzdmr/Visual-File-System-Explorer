// Importing Iterator as required.
import java.util.Iterator;

// This class represents the linked structure that will store the
// information of the file objects in the file system.
public class FileStructure {

    // This class has one instance variable.
    private NLNode<FileObject> root;

    // The argument is the name of a file object that will be stored in the root node.
    // If fileObjectName is the name of a file, this FileStructure object will have only one node
    // storing the fileObject representing the file. Otherwise, this fileStructure object will have
    // a list with nodes for all the file objects contained inside the folder named fileObjectName.
    public FileStructure(String fileObjectName) throws FileObjectException {

        // Creating a new FileObject with the given fileObjectName
        FileObject rootFileObject = new FileObject(fileObjectName);
        // Creating a new NLNode with the rootFileObject and null as the parent, and assigning it to the root variable.
        root = new NLNode<>(rootFileObject, null);
        // Processing the root node (file or directory) recursively.
        processNodeRecursively(root);

    }
    // Creating a recursive method to process a given node in the file structure.
    private void processNodeRecursively(NLNode<FileObject> r) {

        // Getting the FileObject associated with the current node r.
        FileObject f = r.getData();

        // Checking if f is a directory; if it isn't, then it's a file.
        if (f.isDirectory()) {
            // Processing the directory and its contents.
            // Getting an iterator for the files contained in the directory.
            Iterator<FileObject> directoryFiles = f.directoryFiles();
            // Iterate through the files in the directory.
            while (directoryFiles != null && directoryFiles.hasNext()) {
                // Getting the next file in the iterator.
                FileObject childFileObject = directoryFiles.next();
                // Creating a new NLNode with the childFileObject and the current node r as its parent.
                NLNode<FileObject> childNode = new NLNode<>(childFileObject, r);
                // Adding the childNode to r's children.
                r.addChild(childNode);
                // Recursively processing the childNode
                processNodeRecursively(childNode);
            }
        }

    }

    // Returns the root node.
    public NLNode<FileObject> getRoot() { return root; }

    // This method returns a String iterator with the names of all the
    // files of the specified type represented by nodes of this FileStructure.
    public Iterator<String> filesOfType(String type) {

        // Calling the collectingFilesByType method with the root node, the given type,
        // and an empty String array to collect the file names.
        String[] filesArray = collectingFilesByType(root, type, new String[0]);
        // Creating a new Iterator of type String to iterate over the file names
        Iterator<String> iterator = new Iterator<String>() {
            // Initializing the index variable to 0 for keeping track of the position
            // in the filesArray during iteration
            private int index = 0;
            // The hasNext method returns true if there are more file names to iterate
            public boolean hasNext() {
                return index < filesArray.length;
            }
            // The next method returns the next file name in the filesArray and increments
            // the index by 1.
            public String next() {
                return filesArray[index++];
            }
        };
        return iterator; // Return the iterator for the file names.

    }
    // This method collects file names of the specified type from the given node and its children.
    private String[] collectingFilesByType(NLNode<FileObject> node, String type, String[] filesList) {

        // Retrieves the FileObject associated with the current node
        FileObject file = node.getData();
        // Checking if the file is a regular file and if its name ends with the specified type
        if (file.isFile() && file.getLongName().endsWith(type)) {
            // Creating a new array with increased size by 1 to accommodate the new file.
            int newSize = filesList.length + 1;
            String[] newArray = new String[newSize];
            // Copying the existing filesList into the newArray.
            for (int i = 0; i < filesList.length; i++) {
                newArray[i] = filesList[i];
            }
            newArray[filesList.length] = file.getLongName(); // Adding the new file name to the newArray.
            filesList = newArray; // Updating the filesList to newArray.
        }
        // Getting an iterator for the children nodes of r.
        Iterator<NLNode<FileObject>> iterator = node.getChildren();
        // Iterating through the child nodes of r.
        while (iterator.hasNext()) {
            // Getting the next child node from the iterator.
            NLNode<FileObject> nextChild = iterator.next();
            // Recursively collecting files of the specified type from the nextChild node.
            filesList = collectingFilesByType(nextChild, type, filesList);
        }
        return filesList; // Returning the filesList with collected file names.

    }


    // This public method takes a file name and returns the full path of the file if found in the FileStructure.
    // Calls the private helper method 'findingFileRecursively' with the root node and the file name to search for.
    public String findFile(String name) { return findingFileRecursively(root, name); }

    // This private method searches for a file with the given name recursively within the node r and its children.
    private String findingFileRecursively(NLNode<FileObject> r, String name) {

        // Retrieves the FileObject f associated with the current node r.
        FileObject f = r.getData();
        // Checking if f is a regular file and if its name matches the given name.
        if (f.isFile() && f.getName().equals(name)) {
            // If a match is found, returns the full path of the file.
            return f.getLongName();
        }

        // Getting an iterator for the children nodes of r.
        Iterator<NLNode<FileObject>> iterator = r.getChildren();
        // Iterating through the child nodes of r.
        while (iterator.hasNext()) {
            // Getting the next child node from the iterator.
            NLNode<FileObject> childNode = iterator.next();
            // Recursively searches for the file with the given name in the childNode.
            String result = findingFileRecursively(childNode, name);
            // If a non-empty result is found, returns it.
            if (!result.isEmpty()) {
                return result;
            }
        }
        return ""; // If the file with the given name is not found, returns an empty string.
    }

}
