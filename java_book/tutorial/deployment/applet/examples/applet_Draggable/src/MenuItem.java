/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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
public class MenuItem {
    public String name = null;
    public String type = null;
    public String desc = null;
    public String imagePath = null;
    public int price = 0;
    public int qtyOrdered = 0;

    public static MenuItem[] initMenu() {
        MenuItem [] items = new MenuItem[4];

        MenuItem mi = new MenuItem();
        mi.name = "Tuscan Bruschetta";
        mi.type = "Appetizer";
        mi.desc = "A traditional topping of roma tomatoes, basil and extra-virgin olive oil. Served with toasted sourdough bread.";
        mi.imagePath = "images/bruschetta.jpg";
        mi.price = 4;
        items[0] = mi;

        mi = new MenuItem();
        mi.name = "Italian Flatbread";
        mi.type = "Appetizer";
        mi.desc = "Mozzarella cheese, tomatoes and basil on a light flatbread crust with garlic and red bellpepper tapenade.";
        mi.imagePath = "images/flatbread.jpg";
        mi.price = 5;
        items[1] = mi;

        mi = new MenuItem();
        mi.name = "Chicken-Gnocchi";
        mi.type = "Soup";
        mi.desc = "A creamy soup made with roasted chicken, Italian dumplings and basil. Served with salad and breadsticks.";
        mi.imagePath = "images/chicken_gnocchi_soup.jpg";
        mi.price = 7;
        items[2] = mi;

        mi = new MenuItem();
        mi.name = "Minestrone";
        mi.type = "Soup";
        mi.desc = "Fresh locally grown vegetables, beans and pasta in a tomato broth. Served with salad and breadsticks.";
        mi.imagePath = "images/minestrone.jpg";
        mi.price = 8;
        items[3] = mi;

        return items;
    }

}
