package day17;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.List;

public class StreamBasicsTest {
    @Test
    void filterEven_withEmptyList_returnsEmptyList(){

        List<Integer> numbers = new ArrayList<>();
        List<Integer> result = StreamBasics.filterEven(numbers);

        assertTrue(result.isEmpty());
    }

    @Test
    void filterEven_withAllOddNumbers_returnsEmptyList(){
        List<Integer> numbers = List.of(1,3,5,7,9);
        List<Integer> result = StreamBasics.filterEven(numbers);
        assertTrue(result.isEmpty());
    }
    @Test
    void filterEven_withMixedNumbers_returnsOnlyEvenNumbers(){
        List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);
        List<Integer> result = StreamBasics.filterEven(numbers);

        assertEquals(5 , result.size());
        assertTrue(result.stream().allMatch(n -> n % 2 ==0));
        assertFalse(result.contains(3));
    }
    @Test
    void filterEven_withNullList_throwsException(){
        assertThrows(NullPointerException.class , ()-> StreamBasics.filterEven(null));
    }

    @Test
    void uppercaseNonBlank_withEmptyList_returnsEmptyList(){
        List<String> input = new ArrayList<>();
        List<String> result = StreamBasics.uppercaseNonBlank(input);

        assertTrue(result.isEmpty());
    }

    @Test
    void uppercaseNonBlank_withNullBlank_returnsEmptyList(){
        List<String> input = new ArrayList<>();
        input.add(null);
        input.add(" ");
        List<String> result = StreamBasics.uppercaseNonBlank(input);
        assertTrue(result.isEmpty());

    }
    @Test
    void uppercaseNonBlank_withMixList_returnsOnlyUppercaseNonBlankValues(){
        List<String> input = new ArrayList<>();
        input.add("Hello");
        input.add(null);
        input.add("World");
        input.add(" ");

        List<String> result = StreamBasics.uppercaseNonBlank(input);

        assertEquals(2 , result.size());
        assertTrue(result.contains("HELLO"));
        assertFalse(result.contains(null));
        assertFalse(result.contains(" "));
    }

    @Test
    void uppercaseNonBlank_withNullList_throwsException(){
        assertThrows(NullPointerException.class , ()-> StreamBasics.uppercaseNonBlank(null));
    }
    @Test
    void sum_withEmptyList_returnsZero() {
        List<Integer> numbers = List.of();

        int result = StreamBasics.sum(numbers);

        assertEquals(0, result);
    }

    @Test
    void sum_withPositiveNumbers_returnsCorrectSum() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        int result = StreamBasics.sum(numbers);

        assertEquals(15, result);
    }

    @Test
    void sum_withMixedNumbers_returnsCorrectSum() {
        List<Integer> numbers = List.of(-2, -1, 0, 1, 2);

        int result = StreamBasics.sum(numbers);

        assertEquals(0, result);
    }

    @Test
    void sum_withNullList_throwsException() {
        assertThrows(NullPointerException.class, () -> StreamBasics.sum(null));
    }

    @Test
    void sortedDistinct_withEmptyList_returnsEmptyList() {
        List<Integer> numbers = List.of();

        List<Integer> result = StreamBasics.sortedDistinct(numbers);

        assertTrue(result.isEmpty());
    }

    @Test
    void sortedDistinct_withNoDuplicates_returnsSortedList() {
        List<Integer> numbers = List.of(5, 3, 1, 4, 2);

        List<Integer> result = StreamBasics.sortedDistinct(numbers);

        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    @Test
    void sortedDistinct_withDuplicates_returnsSortedUniqueList() {
        List<Integer> numbers = List.of(5, 3, 5, 2, 3, 1);

        List<Integer> result = StreamBasics.sortedDistinct(numbers);

        assertEquals(List.of(1, 2, 3, 5), result);
    }

    @Test
    void sortedDistinct_withNullList_throwsException() {
        assertThrows(NullPointerException.class, () -> StreamBasics.sortedDistinct(null));
    }

}
