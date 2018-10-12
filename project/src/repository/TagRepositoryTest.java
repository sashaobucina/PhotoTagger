package repository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TagRepositoryTest {
	
	private static TagRepository testRepository;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Start with empty file
		testRepository = new TagRepository("test.txt");
		
		if (testRepository.getTags().size() > 0) {
			fail("test.txt should be empty!");
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		testRepository.clear();
		testRepository.saveAllToFile();		
	}

	@Before
	public void setUp() throws Exception {
		testRepository.clear();
		
		testRepository.add("Tag1");
		testRepository.add("Tag2");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTags() {
		
		if (testRepository.getTags().size() != 2) {
			fail("Get tags failed!");
		}
		
		assertTrue("First tag not as expected", testRepository.getTags().get(0).equals("Tag1"));
		assertTrue("Second tag not as expected", testRepository.getTags().get(1).equals("Tag2"));
	}

	@Test
	public void testSaveAllToFile() {
		
		try {
			testRepository.saveAllToFile();
		} catch (IOException e) {
			fail("Unable to save tags to file");
		}
		
		// Create new repository from the same file
		TagRepository repo = new TagRepository("test.txt");

		
		// repositories are equal at this point
		assertTrue("Tag repos are not the same!", repo.getTags().equals(testRepository.getTags()));
	}

	@Test
	public void testAdd() {
		
		// this test case starts with empty repository
		testRepository.clear();
		
		// test add tag
		testRepository.add("Tag1");
		
		assertTrue("Add tag failed!", testRepository.getTags().size() == 1);
		
		// test correct item inserted
		List<String> outcome = new ArrayList<String>();
		outcome.add("Tag1");

		assertTrue("Wrong tag inserted!", testRepository.getTags().equals(outcome));
	}

	@Test
	public void testDelete() {
		
		testRepository.delete("Tag2");
		
		assertTrue("Delete tag failed!", testRepository.getTags().size() == 1);
		
		// test correct item deleted
		List<String> outcome = new ArrayList<String>();
		outcome.add("Tag1");
		
		assertTrue("Delete tag by name failed!", testRepository.getTags().equals(outcome)); 

	}

	@Test
	public void testDeleteAtIndex() {
		
		testRepository.deleteAtIndex(0);

		// test correct item deleted
		List<String> outcome = new ArrayList<String>();
		outcome.add("Tag2");
		
		assertTrue("Delete tag at index failed!", testRepository.getTags().equals(outcome)); 
	}

	@Test
	public void testClear() {
		
		testRepository.clear();
	
		if (testRepository.getTags().size() != 0) {
			fail ("Clear all tags failed!");
		}
		
	}

}

