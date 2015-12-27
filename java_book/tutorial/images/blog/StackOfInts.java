/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

public class StackOfInts {
    private int[] stack;
      //index of last item in the stack + 1
      private int next = 0;
      
      public StackOfInts(int size) {
      //create an array large enough to hold the stack
      stack = new int[size];
      }
      public void push(int on) {
      //push an integer onto the stack
        if (next < stack.length) {
            stack[next++] = on;
        }
      }
      public boolean isEmpty() {
      //check if the stack is empty
        return (next == 0);
      }
      public int pop(){
      //pop an integer from the stack
          if (!isEmpty()) {
            // next item in the stack
            return stack[--next];
        } else {
            return 0;
        }
      }
      public int getStackSize() {
      //get the size of the stack
        return next;
      }

    //class that steps through the stack
    //not related to the stack functionality.
    private class StepThrough {
        // start stepping through the stack at i=0 with step 2
        private int i = 0;
        // retrieve the current element
        
        public int getNext() {
        //get a next value in the stack
            int retValue = stack[i];
            if (i < stack.length) {
                i = i + 2;
            }
            return retValue;
        }
        //last element in the stack?
        public boolean hasNext(){
            return (i < getStackSize());
        }
    }
    //utility method for creating a new StepThrough class instance
    public StepThrough stepThrough() {
        return new StepThrough();
    }
    public static void main(String[] args) {
        //instantiate the outer class as "stackOne"
        StackOfInts stackOne = new StackOfInts(15);
        //populate the stackOne
        System.out.print("Pushing elements into the stack: ");
        for (int j = 0 ; j < 15 ; j++) {
            System.out.print("" + j + " ");
            stackOne.push(j);
        }
        System.out.println();
        System.out.println("Iterating through the whole stack selecting only even indexes:");
        //instantiate the inner class as "iterator"
        StepThrough iterator = stackOne.stepThrough();
        //print out the stackOne[i]
        while(iterator.hasNext()) {
             System.out.print(iterator.getNext() + " ");
        }
        System.out.println();
        System.out.println("Popping all elements from the stack:");
        for (int j = 0 ; j < 15 ; j++) {
             System.out.print(stackOne.pop() + " ");
        }     
   }
}
