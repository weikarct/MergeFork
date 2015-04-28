/*
 * A simple linked list implementation.
 *
 * K. Weber weberk@mountunion.edu
 *
 * Original version from 2009 (or earlier).
 * March 16, 2015: Modified to be include in CSC 220 Lab 7.
 *                 Strips down to the add and iterator methods.
 * March 21, 2015: Changed SimpleLinkedListIterator to use nextNode instance
 *                 variable instead of current.
 * March 24, 2015: Provided explicit constructors for SimpleLinkedList and
 *                 SimpleLinkedListIterator.
 * 
 */

package csc220program5weikarct;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedList<E> implements Iterable<E>
{
    private static class Node<E>
    {
        public E data;
        public Node<E> next;
    }
    
    Node<E> first, last;
    
    public SimpleLinkedList()
    {
        first = null;
        last = null;
    }
    

    public void add(E e)
    {
        Node<E> newNode = new Node();
        newNode.data = e;
        newNode.next = null;
        if (first == null)  
        {
            first = newNode;
        }
        else                
        {
            last.next = newNode;
        }
        last = newNode;
    }
    
    public void reverse()
    {
        reverse(first);
      
        Node<E> tmp = first;
        first = last;
        last = tmp;
    }
    private void reverse(Node<E> oldFirst)
    {
        if (oldFirst != null && oldFirst.next != null)  
        {
            reverse(oldFirst.next);
            oldFirst.next.next = oldFirst;
            oldFirst.next = null;
        }
    }
    @Override
    public Iterator<E> iterator(){return new SimpleLinkedListIterator();}
    
   
    private class SimpleLinkedListIterator implements Iterator<E>
    {
    
        Node<E> nextNode;
       
        Node<E> currNode;
        Node<E> prevNode;
        
        public SimpleLinkedListIterator(){nextNode = first;}
       
        
        @Override
        public boolean hasNext()
        {
            return nextNode != null;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            E tmpData = nextNode.data;
            nextNode = nextNode.next;
            return tmpData;
        }
        
        @Override
        public void remove()
         {
            if (currNode == null)
            if(prevNode != null)
            {
                prevNode.next = nextNode;
                currNode = null;
            }
            else
               first = nextNode;
        
           if(currNode == last)
           {
               last = prevNode;
           }
            currNode = null;
           }
    }
}
    
   

