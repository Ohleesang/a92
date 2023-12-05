/**
 * priorities : 프로세스의 중요도로 담긴배열
 * location : 몇번쨰로 실행되는지 알고싶은 순서
 *
 * 이때, 몇번째로 실행되는지 확인후 return
 *
 * 우선 순의 큐!
 * 그러니까 큐에 다차야 프로그램이 실행되는건가?
 *
 * 1. 실행 대기 큐(Queue)에서 대기중인 프로세스 하나를 꺼냅니다.
 * 2. 큐에 대기중인 프로세스 중 우선순위가 더 높은 프로세스가 있다면 방금 꺼낸 프로세스를 다시 큐에 넣습니다.
 * 3. 만약 그런 프로세스가 없다면 방금 꺼낸 프로세스를 실행합니다.
 *   3.1 한 번 실행한 프로세스는 다시 큐에 넣지 않고 그대로 종료됩니다.
 *
 *   2 1 3 2 가 입력값
 *
 *   que 2
 *   que 2 1
 *   que 2 1 3  ==> que 3 2 1 //이때 3의 index 값을 저장
 *   que [3] 2 1 2   첫번째 실행
 *
 *   que.dequeue() ! // 이떄 디큐 했을떄, index값 이랑 location 이 같다면 그값 + 1 =answer
 *   // 디큐 이후, que 에 있는게 입력값
 *
 *   que 2
 *   que 2 1
 *   que [2] 1 2    두번쨰 실행
 *
 *   que.dequeue()!
 *   que 1
 *   que 1 2  ==> que 2 1
 *   que [2] 1     세번쨰 실행
 *
 *   que [1]       네번쨰 실행
 *
 *   이때 순서를 어떻게 찾아야하나...
 *
 *   3 2 2 1 순으로 실행 근데, 만약 2가 중복되어있으니까 이걸 ...
 *   c d a b
 *
 *   ===> 아싸리 받는값을 pair로 받아?
 */

class Que{
    val MAX_NUM = 500 //왜 500되면 통과되냐...?? 100은 안되고
    var queue = Array<Pair<Int,Int>>(MAX_NUM){Pair(-1,-1)}
    var front = 0
    var rear = -1
    var size = 0

    fun insert(p :Pair<Int,Int>):Pair<Int,Int>{
        rear = (rear+1)%MAX_NUM
        queue[rear] = p
        size++
        return queue[rear]
    }

    fun delete():Pair<Int,Int>{
        var s = peek()
        front = (front+1)%MAX_NUM
        size--

        return s
    }

    fun peek():Pair<Int,Int>{
        return queue[front]
    }

    fun isEmpty():Boolean{
        return size == 0
    }
    fun max():Int{//max 값을 찾아주는 함수

        //단,범위내에서 max 값을 찾아줘야함
        var temp = queue.sliceArray(front..rear)
        var i = temp.maxOf{it.second}
        return i
    }
}
class Solution {
    fun solution(priorities: IntArray, location: Int): Int {
        var answer = 0
        var maps = priorities.mapIndexed { index, i -> index to i }
        var que = Que()

        //que 에 초기값 입력
        maps.forEach{
            que.insert(it) //
        }

        var max = 0
        var count = 0 // 몇번 실행됬는지!
        while(!que.isEmpty()){
            //max 값을 찾는다
            max = que.max()
            //max 값이 나올떄 까지 디큐,이큐 실행
            while(que.peek().second!=max){
                que.insert(que.delete())
            }
            count++
            //max 값이 나오면 location 의 값을 비교 하여 값이 맞다면 return
            if(que.peek()==maps[location]){
                answer = count
                break
            }
            //만약 값이 아니면 디큐
            que.delete()

        }
        answer = count
        return answer
    }
}

fun main(){

    var a =Solution()

//    a.solution(intArrayOf(2,1,3,2),2) //1
    //c d a b 순으로 2 3 0 1
//    a.solution(intArrayOf(1,1,9,1,1,1),0) //5
    // c d e f a b 순으로  2 3 4 0 1
    a.solution(intArrayOf(3),0) //4

}