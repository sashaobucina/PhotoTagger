package service;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.FileInfo;

public class TagFileManagerTest {
	
	/** file name of test image */
	private static String  testImg = "animal.jpg";
	
	/** representation of test image file */
	private static FileInfo animal = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		animal = new FileInfo(testImg, "./");
	}

	@After
	public void tearDown() throws Exception {
		TagFileManager.getInstance().clearAllTags(animal);
		
	}

	@Test
	public void testAddTagToFile() {
		
        // rename the file (add tag to file name)
        FileInfo renamedFileInfo = TagFileManager.getInstance().addTagToFile(animal, "beauty");
        
        // check old file doesn't exist
        File oldFile = new File(animal.getAbsolutePath());
		assertTrue("Original file was not deleted", !oldFile.exists());
		
		// check new file exists
        File taggedFile = new File(renamedFileInfo.getAbsolutePath());
		assertTrue("New file was not created", taggedFile.exists());
		
		// check file name
		assertTrue("File name is incorrect", renamedFileInfo.getName().equals("animal@beauty.jpg"));
		
		animal = renamedFileInfo;
	}

	@Test
	public void testAddMultipleTags() {
		
		List<String> tags = new ArrayList<String>();
		tags.add("donkey");
		tags.add("grey");
		
        // rename the file (add tag to file name)
        FileInfo renamedFileInfo = TagFileManager.getInstance().addMultipleTags(animal, tags);
        
        // check old file doesn't exist
        File oldFile = new File(animal.getAbsolutePath());
		assertTrue("Original file was not deleted", !oldFile.exists());
		
		// check new file exists
        File taggedFile = new File(renamedFileInfo.getAbsolutePath());
		assertTrue("New file was not created", taggedFile.exists());
		
		// check file name
		assertTrue("File name is incorrect", renamedFileInfo.getName().equals("animal@donkey@grey.jpg"));
		
		animal = renamedFileInfo;

	}

	@Test
	public void testClearAllTags() {
		
		List<String> tags = new ArrayList<String>();
		tags.add("donkey");
		tags.add("grey");
		
        // rename the file (add tags to file name)
        FileInfo taggedFileInfo = TagFileManager.getInstance().addMultipleTags(animal, tags);
        
        // original file is deleted
        File original = new File(animal.getAbsolutePath());
		assertTrue("Original file was not deleted", !original.exists());
		
		// restore original file (clear all tags)
		TagFileManager.getInstance().clearAllTags(taggedFileInfo);
		
        // original file recovered
        original = new File(animal.getAbsolutePath());
		assertTrue("Original file was not deleted", original.exists());
	}
	
//	@Test
//	public void testRenameImageFile() {
//		
//		String newName = animal.getParent() + "@donkey" + animal.getName();
//		FileInfo renamedFileInfo = TagFileManager.getInstance().renameImageFile(animal, newName);
//		
//		// original File was deleted
//		File original = new File(animal.getAbsolutePath());
//		assertTrue("Original file was not deleted", !original.exists());
//		
//		// restore original file (clear all tags)
//		TagFileManager.getInstance().clearAllTags(renamedFileInfo);
//		
//		// original file recovered
//        original = new File(animal.getAbsolutePath());
//		assertTrue("Original file was not deleted", original.exists());
//	}

}
