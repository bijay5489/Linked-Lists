# Linked-Lists
Assignment Context: Continue exploring the implementation of a "sorted sequence" ADT using a linked-list structure, based on Chapter 4, specifically Section 4.5 of the textbook.
Task: Re-implement the ApptBook ADT with a linked list instead of a previously used structure, with no initial capacity specification.
Data Structure Changes:
Implement a node class as a private static nested class within the ApptBook class.
Node class should contain an Appointment data field and a next node field, with a constructor for these fields.
Omit the "tail" field from the data structure as it is unnecessary for the methods implemented.
Cursor and Precursor Management:
Manage "no current element" condition by setting the cursor to null and the precursor to the last node if present.
Method Requirements:
Implement a wellFormed method to check the data structure's integrity.
Unlike previous assignments, cloning will require deep copying of the list.
Invariant Conditions:
Ensure no cycles in the list.
All data must be non-null and sorted in non-decreasing order.
Precursor must either be null or point to an existing node.
Cursor positioning rules relative to the precursor.
manyNodes field must accurately count the nodes.
Testing and Code Provided:
Some invariant checks are provided; others must be implemented by the student.
Various test files included in the repository to assist with validation.
Submission Instructions:
Assignment is due on Monday, October 3, at 10:00 PM.
Use the provided GitHub classroom link to access and submit the assignment.
