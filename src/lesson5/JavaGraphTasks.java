package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */

    // Трудоемкость Т = О(N)
    // Ресурсоемкость R = О(N)
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        List<Graph.Edge> result = new LinkedList<>();
        if (!isItContainsEulerLoop(graph)) return result;
        Set<Graph.Edge> setOfVisitedEdges = new HashSet<>();
        Stack<Graph.Vertex> stack = new Stack<>();
        stack.push(graph.getRandomVertex());
        while (graph.getEdges().size() != result.size()) {
            Graph.Vertex vertex = stack.peek();
            Graph.Edge edge = null;
            for (Graph.Edge e : graph.getConnections(vertex).values()) {
                if (!setOfVisitedEdges.contains(e)) {
                    edge = e;
                }
            }
            if (edge != null) {
                if (vertex == edge.getBegin()) stack.push(edge.getEnd());
                else stack.push(edge.getBegin());
                setOfVisitedEdges.add(edge);
                result.add(edge);
            } else {
                stack.pop();
                result.remove(result.size() - 1);
            }
        }
        return result;
    }

    private static boolean isItContainsEulerLoop(Graph graph) {
        for (Graph.Vertex vertex : graph.getVertices()) {
            int countOfNeighbors = graph.getNeighbors(vertex).size();
            if (countOfNeighbors == 0 || countOfNeighbors % 2 == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    // Трудоемкость Т = О(кол-во узлов * кол-во ребер * кол-во независимых множеств вершин)
    // Ресурсоемкость R = О(N)
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        Set<Graph.Vertex> candidates = graph.getVertices();
        Set<Graph.Vertex> not = new HashSet<>();
        Set<Graph.Vertex> indVertexSet = new HashSet<>();
        Set<Graph.Vertex> maxIndVertexSet = new HashSet<>();
        return findIndependentVertexSet(graph, indVertexSet, maxIndVertexSet, candidates, not);
    }

    private static Set<Graph.Vertex> findIndependentVertexSet(Graph graph, Set<Graph.Vertex> maxIndVertexSet,
                                                              Set<Graph.Vertex> indVertexSet,
                                                              Set<Graph.Vertex> candidates, Set<Graph.Vertex> not) {
        while (checkCondition(graph, candidates, not)) {
            Graph.Vertex vertex = candidates.stream().findAny().get();
            indVertexSet.add(vertex);
            Set<Graph.Vertex> newCandidates = new HashSet<>(candidates);
            Set<Graph.Vertex> newNot = new HashSet<>(not);
            newCandidates.removeIf(v -> graph.getNeighbors(v).contains(vertex));
            newNot.removeIf(v -> graph.getNeighbors(v).contains(vertex));
            newCandidates.remove(vertex);
            if (newCandidates.isEmpty() && newNot.isEmpty()) {
                if (indVertexSet.size() > maxIndVertexSet.size()) {
                    maxIndVertexSet = new HashSet<>(indVertexSet);
                }
            } else {
                maxIndVertexSet = findIndependentVertexSet(graph, maxIndVertexSet, indVertexSet, newCandidates, newNot);
            }
            indVertexSet.remove(vertex);
            candidates.remove(vertex);
            not.add(vertex);
        }
        return maxIndVertexSet;
    }

    private static boolean checkCondition(Graph graph, Set<Graph.Vertex> candidates, Set<Graph.Vertex> not) {
        boolean isFind = true;
        if (candidates.isEmpty()) return false;
        for (Graph.Vertex n : not) {
            for (Graph.Vertex c : candidates) {
                isFind = graph.getNeighbors(n).contains(c);
                if (isFind) break;
            }
            if (!isFind) return false;
            isFind = false;
        }
        return true;
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        throw new NotImplementedError();
    }
}