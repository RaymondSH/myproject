import java.util.*;

/**
 * Created by Raymond on 2019/1/25.
 */
public class BackTracking {

    //17 电话号码的组合
    private String[] letterToDigit = new String[]{
             "abc","def","ghi","jkl","mon","pqrs","tuv","wyxz"
            // 2,    3,     4,    5,     6,     7,     8,     9
    };
    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if (digits.length() == 0){
            return list;
        }
        findAllCombinations(digits,0,"",list);
        return list;
    }
    private void findAllCombinations(String digits,int index,String str,List<String> list){
        //递归的终止条件
        if (index == digits.length()) {
            list.add(str);
            return;
        }
        //递归的处理过程
        char c = digits.charAt(index);//获取下一个数字
        String letters = letterToDigit[c - '2']; //将数字转换为相应的字符串
        for (int i = 0; i < letters.length(); i++) {
            findAllCombinations(digits,index + 1,str + letters.charAt(i),list);
        }
    }

    //93 复原IP地址
    public List<String> restoreIpAddresses(String s) {
        List<String> ipList = new ArrayList<>();
        if (s.length() > 12 || s.length() < 4){
            return ipList;
        }
        findAllIpAddr(s, 0, 4, "", ipList);
        return ipList;
    }
    private void findAllIpAddr(String str,int loc,int deep,String ip,List<String> ipList){
        String end = str.substring(loc);
        if (deep == 1 && Integer.parseInt(end) <= 255){
            if (end.startsWith("0") && end.length() > 1){
            }else {
                ipList.add(ip + str.substring(loc));
            }
            return;
        }
        for (int i = loc; i < loc + 3; i++) {
            String subIp,subStr;
            if (i + 1 < str.length()){
                subIp = str.substring(loc,i + 1);
                subStr = str.substring(i + 1,str.length());
                if (subIp.length() > 1 && subIp.startsWith("0")) {
                } else if (Integer.parseInt(subIp) > 255){
                } else if ((deep - 1 )> subStr.length() || (3 * (deep - 1))< subStr.length()){
                } else {
                    findAllIpAddr(str, i + 1,deep - 1, ip + subIp + ".",ipList);
                }
            }
        }
    }

    //131 分割字符串
    private List<List<String>> palindromes = new ArrayList<>();
    public List<List<String>> partition(String s) {
        if (s.length() == 0 || s == " "){
            return null;
        }
        partition(s,0,new Stack<>());
        return palindromes;
    }
    private void partition(String str,int loc,Stack<String> palindrome){
        //递归终止条件
        if (str.length() == loc){
            ArrayList<String> res = new ArrayList<>();
            for (int i = 0; i < palindrome.size(); i++) {
                res.add(palindrome.get(i));
            }
            palindromes.add(res);
            return;
        }
        for (int i = loc; i < str.length(); i++) {
            //如果左边部分为回文串，则对右边进行递归，否则，进行下一次迭代
            if (isPalindrome(str,loc,i)){
                String left = str.substring(loc,i + 1);
                palindrome.push(left);
                partition(str,i + 1,palindrome);
                palindrome.pop();
            }
        }
    }
    private boolean isPalindrome(String str,int left,int right){
        int dif = right - left;
        for (int i = 0; i <= dif; i++) {
            if (str.charAt(left++) != str.charAt(right--))
                return false;
        }
        return true;
    }

    //46 全排列（无重复元素）
    private boolean[] used;
    private List<List<Integer>> permute = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        if (nums.length == 0)
            return null;
        ArrayList<Integer> list = new ArrayList<>();
        used = new boolean[nums.length];
        Arrays.fill(used,false);
        permute(nums,0,list);
        return permute;
    }
    private void permute(int[] nums,int loc,ArrayList<Integer> list){
        if (nums.length == loc){
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                res.add(list.get(i));
            }
            permute.add(res);
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]){
                list.add(nums[i]);
                used[i] = true;
                permute(nums,loc + 1,list);
                list.remove((Object)Integer.valueOf(nums[i]));
                used[i] = false;
            }
        }
    }

    //47 全排列（有重复元素）
    public List<List<Integer>> permuteUnique(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        used = new boolean[nums.length];
        Arrays.fill(used,false);
        Arrays.sort(nums);
        permuteUnique(nums,0,stack);
        return permute;
    }
    private void permuteUnique(int[] nums,int loc,Stack<Integer> stack){
        if (nums.length == loc){
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                res.add(stack.get(i));
            }
            permute.add(res);
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!used[i] && isDuplicate(nums, i)){
                used[i] = true;
                stack.push(nums[i]);
                permuteUnique(nums, loc + 1, stack);
                stack.pop();
                used[i] = false;
            }
        }
    }
    private boolean isDuplicate(int[] nums, int index){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < index; i++) {
            if (!used[i]){
               map.put(i,nums[i]);
            }
        }
        if (map.containsValue(nums[index]))
            return false;
        else
            return true;
    }

    //77 组合
    private List<List<Integer>> combine = new ArrayList<>();
    public List<List<Integer>> combine(int n, int k) {
        combine(n, k,1,new Stack<>());
        return combine;
    }
    private void combine(int n,int k,int loc,Stack<Integer> stack){
        if (stack.size() == k) {
            List<Integer> list = new ArrayList<>(stack);
            combine.add(list);
            return;
        }
        for (int i = loc; i <= n - (k - stack.size()) + 1; i++) {
            stack.push(i);
            combine(n, k, i + 1, stack);
            stack.pop();
        }
    }

    //39 组合总和
    private List<List<Integer>> combinationSum = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length == 0 || target == 0)
            return combinationSum;
        combinationSum(candidates,target,0,new Stack<>());
        return combinationSum;
    }
    private void combinationSum(int[] candidates, int target,int loc,Stack<Integer> stack){
        if (target == 0){
            List<Integer> list = new ArrayList<>(stack);
            combinationSum.add(list);
            return;
        }
        for (int i = loc; i < candidates.length; i++) {
            if (target >= candidates[i]) {
                stack.push(candidates[i]);
                combinationSum(candidates, target - candidates[i],i, stack);
                stack.pop();
            }
        }
    }

    //40 组合总和2
    private List<List<Integer>> combinationSum2 = new ArrayList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        combinationSum2(candidates,target,0,new ArrayList<>());
        return combinationSum2;
    }
    private void combinationSum2(int[] arr,int target,int loc,List<Integer> current){
        if (target == 0){
            List<Integer> list = new ArrayList<>(current);
            combinationSum2.add(list);
            return;
        }else {
            for (int i = loc; i < arr.length; i++) {
                if (target >= arr[i]) {
                    if (i > loc && arr[i] == arr[i - 1])
                        continue;
                    current.add(arr[i]);
                    combinationSum2(arr, target - arr[i], i + 1, current);
                    current.remove(current.size() - 1);
                }
            }
        }
    }

    // 216 组合总和3
    private List<List<Integer>> combinationSum3 = new ArrayList<>();
    private boolean isContinue = false;
    public List<List<Integer>> combinationSum3(int k, int n) {
        combinationSum3(k,n,0,1,new Stack<>());
        return combinationSum3;
    }
    private void combinationSum3(int k,int n,int sum,int start,Stack<Integer> stack){
        if (stack.size() == k){
            if (sum == n) {
                List<Integer> list = new ArrayList<>(stack);
                combinationSum3.add(list);
                isContinue = true;
            }
            return;
        }
        for (int i = start; i < 10; i++) {
            stack.push(i);
            combinationSum3(k,n,sum + i,i + 1,stack);
            stack.pop();
            if (isContinue){
                isContinue = false;
                return;
            }
        }
    }

    //78 子集(无重复元素)
    private List<List<Integer>> subSet = new ArrayList<>();
    public List<List<Integer>> subsets(int[] nums) {
        if (nums.length == 0)
            return subSet;
        findAllSubSets(nums,0,new Stack<>());
        return subSet;
    }
    private void findAllSubSets(int[] nums,int loc,Stack<Integer> stack){
        if (loc == nums.length){
            subSet.add(new ArrayList<>(stack));
            return;
        }else {
            stack.push(nums[loc]);
            findAllSubSets(nums, loc + 1, stack);
            stack.pop();
            findAllSubSets(nums, loc + 1, stack);
        }
    }

    //90 子集2 （有重复元素）
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        subSetsWithDup(nums,0,Integer.MAX_VALUE,new Stack<>());
        return subSet;
    }
    private void subSetsWithDup(int[] nums,int loc,int last,Stack<Integer> stack){
        if (loc == nums.length){
            subSet.add(new ArrayList<>(stack));
            return;
        }else {
            if (nums[loc] != last) {
                stack.push(nums[loc]);
                subSetsWithDup(nums, loc + 1, last, stack);
                last = stack.pop();
            }
            subSetsWithDup(nums, loc + 1, last, stack);
        }
    }

    //401 二进制手表
    private List<String> times = new ArrayList<>();
    public List<String> readBinaryWatch(int num) {
        for (int i = 0; i <= num; i++) {
            if (i >= 4)
                calTime(4,num - i,times);
            else
                calTime(i,num - i,times);
        }
        return times;
    }
    private void calTime(int h,int m,List<String> times){ //calculateTime
        int[] hour = new int[]{1,2,4,8};
        int[] minute = new int[]{1,2,4,8,16,32};
        List<Integer> hours = new ArrayList<>();
        List<Integer> minutes = new ArrayList<>();
        cal(hour,h,0,true,new Stack<>(),hours);
        cal(minute,m,0,false,new Stack<>(),minutes);
        String minStr;
        for (int i = 0; i < hours.size(); i++) {
            for (int j = 0; j < minutes.size(); j++) {
                minStr = minutes.get(j).intValue() < 10 ? ("0" + minutes.get(j).toString()) : minutes.get(j).toString();
                times.add(hours.get(i).toString() + ":" + minStr);
            }
        }
    }
    private void cal(int[] num,int k,int loc,boolean isHour,Stack<Integer> stack,List<Integer> list){
        if (stack.size() == k) {
            int sum = 0;
            for (int i = 0; i < stack.size(); i++) {
                sum += stack.get(i);
            }
            if (isHour && sum < 12){
                list.add(sum);
            }
            if (!isHour && sum < 60)
                list.add(sum);
            return;
        }
        for (int i = loc; i < num.length; i++) {
            stack.push(num[i]);
            cal(num,k, i + 1,isHour, stack,list);
            stack.pop();
        }
    }

    // todo 79 单词搜索
    public boolean exist(char[][] board, String word) {
        return false;
    }
    // todo 200 岛屿的个数
    public int numIslands(char[][] grid) {
        return 0;
    }

    //todo 417 太平洋大西洋水流问题
    public List<int[]> pacificAtlantic(int[][] matrix) {
        return null;
    }

    // todo 130 被围绕的区域
    public void solve(char[][] board) {

    }

    //51 N皇后
    private List<List<String>> queens = new ArrayList<>();
    private boolean[] col,leftSlash,rightSlash;
    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        col = new boolean[n];
        leftSlash = new boolean[2 * n - 1];
        rightSlash = new boolean[2 * n - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        solveNQueens(board,0);
        return queens;
    }
    private void solveNQueens(char[][] board,int row){
        if (row == board.length) {
            //将结果放入数组中，返回
            queens.add(boardToList(board));
            return;
        }
        for (int i = 0; i < board.length; i++) {
            //判断[row][i]的左上方，此列，右上方是否有'Q'，
            // 若有，则继续迭代；否则，则将此位置置为Q，继续递归
            if (row == 0 || (!leftSlash[row + i] && !col[i] && !rightSlash[row - i + board.length - 1])){
                board[row][i] = 'Q';
                leftSlash[row + i] = true;
                col[i] = true;
                rightSlash[row - i + board.length - 1] = true;
                solveNQueens(board,row + 1);
                board[row][i] = '.';
                leftSlash[row + i] = false;
                col[i] = false;
                rightSlash[row - i + board.length - 1] = false;
            }
        }
    }
    private List boardToList(char[][] board){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            StringBuffer buffer = new StringBuffer();
            for (int j = 0; j < board.length; j++) {
                buffer.append(board[i][j]);
            }
            list.add(buffer.toString());
        }
        return list;
    }

    //52
    public int totalNQueens(int n){
        return solveNQueens(n).size();
    }

    //todo 37 解数独

}
