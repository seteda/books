package defaultmethods;

import java.util.*;
import java.util.stream.*;
import java.lang.*;
import java.util.function.*;

public interface SortFunction extends Function<Comparator<Card>, Deck> {
    public Deck apply(Comparator<Card> cardComparator);
}
