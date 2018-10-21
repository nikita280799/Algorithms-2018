package lesson2;


import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    // Трудоемкость O(n * m))
    // Ресурсоемкость O(N)
    static public String longestCommonSubstring(String first, String second) {
        int[][] matrix = new int[first.length()][second.length()];
        int max = 0;
        int maxi = 0;
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    matrix[i][j]++;
                    if (i != 0 && j != 0) {
                        matrix[i][j] += matrix[i - 1][j - 1] > 0 ? matrix[i - 1][j - 1] : 0;
                    }
                    if (matrix[i][j] > max) {
                        max = matrix[i][j];
                        maxi = i;
                    }
                }
            }
        }
        if (max == 0) return "";
        return first.substring(maxi + 1 - max, maxi + 1);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    // Трудоемкость O(n * m))
    // Ресурсоемкость O(N)
    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException,
            IllegalFormatException {
        String currentLine;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName));
        List<String> listOfStrings = new ArrayList<>();
        while ((currentLine = bufferedReader.readLine()) != null) {
            listOfStrings.add(currentLine);
        }
        int height = listOfStrings.size();
        int width = listOfStrings.get(0).length() / 2 + 1;
        char[][] matrix = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = listOfStrings.get(i).charAt(j * 2);
            }
        }
        Set<String> result = new HashSet<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (String word : words) {
                    if (word.charAt(0) == matrix[i][j] && search(word, 0, i, j, matrix)) {
                        result.add(word);
                    }
                }
            }
        }
        return result;
    }

    private static boolean search(String word, int curLetter, int i, int j, char[][] matrix) {
        boolean upSearch = false, downSearch = false, leftSearch = false, rightSearch = false;
        curLetter++;
        if (curLetter == word.length()) return true;
        if (i != 0 && matrix[i - 1][j] == word.charAt(curLetter)) upSearch = search(word, curLetter, i - 1, j, matrix);
        if (i != matrix.length - 1 && matrix[i + 1][j] == word.charAt(curLetter))
            downSearch = search(word, curLetter, i + 1, j, matrix);
        if (j != matrix[i].length - 1 && matrix[i][j + 1] == word.charAt(curLetter))
            rightSearch = search(word, curLetter, i, j + 1, matrix);
        if (j != 0 && matrix[i][j - 1] == word.charAt(curLetter))
            leftSearch = search(word, curLetter, i, j - 1, matrix);
        return upSearch || downSearch || leftSearch || rightSearch;
    }
}