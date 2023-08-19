import java.util.ArrayList;
import java.util.Iterator;
import java.util.List; 

public class FileStructure {
	// instance variable
	private NLNode<FileObject> root; 	// reference to root node
	
	// constructor 
	public FileStructure(String fileObjectName) throws FileObjectException {
		FileObject file = new FileObject(fileObjectName); 	// initialize root file object  
		
		// if fileObjectName is the name of a folder: 
		if (file.isDirectory()) {
			// make a root node 
			root = new NLNode<FileObject>(file, root); 
			
			Iterator<FileObject> fileIterator = file.directoryFiles(); //  iterator of directory files 
			
			while (fileIterator.hasNext()) {
				FileObject child = fileIterator.next();
            	NLNode<FileObject> childNode = new NLNode<FileObject>(child, root);
            	root.addChild(childNode);		// add child node to root 
            	
            	// if child is a folder or directory: 
            	if (child.isDirectory()) {
            		buildFileStructure(childNode); 
            	}
            }
		}
        else {
			root = new NLNode<FileObject>(file, null); 
		}
	}
	
	// auxiliary recursive algorithm to explore the folder and create nodes to file objects 
	private void buildFileStructure(NLNode<FileObject> node) throws FileObjectException {
		FileObject file = node.getData(); 
		
		Iterator<FileObject> iterator = file.directoryFiles(); 
		while (iterator.hasNext()) {
			FileObject child = iterator.next(); 
			NLNode<FileObject> childNode = new NLNode<>(child, node); 
	        node.addChild(childNode); 
	            
	        if (child.isDirectory()) {
	        	buildFileStructure(childNode); 
	            }
			}
	}
	
	public NLNode<FileObject> getRoot() {
		return root; 
	}

	
	public Iterator<String> filesOfType (String type) throws FileObjectException {
		List<String> fileNames = new ArrayList<>(); 
		recursiveMethod(root, type, fileNames); 
		return fileNames.iterator(); 
	}
	
	// recursive auxiliary algorithm 
	private void recursiveMethod(NLNode<FileObject> n, String type, List<String> fileNames) {
		FileObject f = n.getData(); 
		if (f.isFile()) {
			String fileName = f.getLongName(); 
			// if name of the file ends with the specified type: 
			if (fileName.endsWith(type)) {
				fileNames.add(fileName); 	// add name to filenames 
			}
		} 
		else {
			Iterator<NLNode<FileObject>> childIterator = n.getChildren(); 
			while (childIterator.hasNext()) {
				NLNode<FileObject> childNode = childIterator.next();
				recursiveMethod(childNode, type, fileNames); 
	        }
		}
	}
	      
	public String findFile(String name) throws FileObjectException {
		// look for file w specified name inside FileStructure
		if (root == null) {
			return ""; 			// return empty string 
		}
		return fileFinder(root, name); 	// call fileFinder 
	}
	
	// helper method 
	private String fileFinder(NLNode<FileObject> node, String name) {
		FileObject f = node.getData(); 
		if (!f.isDirectory() && f.getName().equals(name)) {		// if file's name is the same as the specified name: 
			return f.getLongName(); 	// return the full name of file 
		}
		
		// recursive case: 
		if (f.isDirectory()) {
			Iterator<NLNode<FileObject>> childIterator = node.getChildren();
		    while (childIterator.hasNext()) {
		    	NLNode<FileObject> child = childIterator.next(); 
		    	String result = fileFinder(child, name); 	
		    	if (!result.equals("")) {		
		    		return result; 	
		    	}
		    }
		}		
	    return ""; 	// return an empty string if there's no file found
	}
	
}
