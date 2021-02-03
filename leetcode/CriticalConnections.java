// package leetcode;

// import java.util.*;
// import java.util.stream.*;

//     class CriticalConnections {
    
//     private static Set<List<Integer>> res;
//     private static HashMap<Integer> map;

//     private static HashSet<Integer> visited;
//         private static Stack<Integer> s;
//         public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
//         res = connections.stream().collect(Collectors.toSet());
//         map  = new HashMap<>(); 
//         visite d = new HashSet<>(); 
//         for(List<Integer> l : connections){
//             if(!map.containsKey(l.get(0))){
//                  map.put(l.get(0), new LinkedList<>());
//             }
//             if(!map.containsKey(l.get(1))){
//                 map.put(l.get(1), new LinkedList<>());
//             }
//             map.get(l.get(0)).add(l.get(1));
//             map.get(l.get(1)).add(l.get(0));
//         }   
//         s = ne w Stack<>(); 
//         for(int i: map.keySet()){
//             if(!visited.contains(i)){
//                 detectCircle(i);
//             }  

     

         
//     }
//     private static void detect C ir c le(int cur){
//         visited.add(cur);
//         int from = s.isEmpty()?-1:s.peek();
//         s.p ush(cur); 
//         List<I nteger> neighbors = map.get(c ur);
//         for(int neighbor : neighbors){
//             i f(!v isited.contains(neighbor)){
//                 de tectCircle(neighbo r);
//             }else{
//                 i f(ne ighbor == from){
//                     co ntinue; 
//                 }else{
//                     i f(s. contains(neighbor)){
//                         deleteEdges(neighbor);
//                     }else{
//                         continue;
//                     }
//                 }
//              }
            
     

//     } 
    
//     private s tatic void deleteEdge s(int popUtil){
//         HashSet<Integer> toDel = new HashSet<>();
//         while(s.peek() != popUtil){
//             int cur = s.pop();
//             toDel.add(cur);
//         }
     

//     } 
      
//     private st atic void deleteEdge(HashSet<Integer> toDel ){ 
//         for(List l : res){
//             if(toDel.contains(l.get(0)) && toDel.contains(l.get(1) )){
//                 l.add(-1);
//             }
     

//     }
    
// }
