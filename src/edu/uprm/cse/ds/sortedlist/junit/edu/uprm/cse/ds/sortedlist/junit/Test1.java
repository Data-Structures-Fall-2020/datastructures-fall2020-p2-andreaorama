package edu.uprm.cse.ds.sortedlist.junit;

import edu.uprm.cse.ds.sortedlist.SortedCircularDoublyLinkedList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class Test1 {

	private SortedCircularDoublyLinkedList<String> L;
	@Before
	public void setUp() throws Exception {
		L = new SortedCircularDoublyLinkedList<String>();
		
	}

	@Test
	public void testOne() {
		
		L.add("Jose");
		L.add("Al");
		assertTrue(L.size()==2);
		assertTrue(L.get(0).equals("Al"));
	}

	@Test
	public void testTwo() {
		
		L.add("Jose");
		L.add("Al");
		L.add("Zen");
		L.add("Cal");
		assertTrue(L.size()==4);
		assertTrue(L.get(L.size()-1).equals("Zen"));
	}

	@Test
	public void testThree() {
		
		L.add("Jose");
		L.add("Al");
		L.add("Zen");
		L.add("Cal");
		assertTrue(L.get(0).equals("Al"));
		int target = L.firstIndex("Cal") + 1;
		assertTrue(L.get(target).equals("Jose"));
	}

	@Test
	public void testFour() {
		
		L.add("Jose");
		L.add("Al");
		L.add("Zen");
		L.add("Cal");
		assertTrue(L.get(0).equals("Al"));
		int target = L.firstIndex("Cal") + 1;
		assertTrue(L.get(target).equals("Jose"));
		L.remove(target);
		assertTrue(L.firstIndex("Jose") < 0);
	}

	@Test
	public void testFive() {
		
		L.add("Jose");
		L.add("Al");
		L.add("Zen");
		L.add("Cal");
		assertTrue(L.get(0).equals("Al"));
		int target = L.firstIndex("Cal") + 1;
		assertTrue(L.get(target).equals("Jose"));
		L.remove(target);
		assertTrue(L.firstIndex("Jose") < 0);
		L.add("Jose");
		assertTrue(L.firstIndex("Jose") == 2);
	}

}
