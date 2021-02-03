public List<String> generateParenthesis(int n) {
   // c.c
   if (n <= 0) {
      throw new IllegalArgumentException();
   }

   List<String> res = new ArrayList<>();
   dfs(res, new StringBuilder(), 0, n);
   return res;
}

private void dfs(List<String> res, StringBuilder path, int delta, int n) {
       int len = path.length();
        if (len == 2 * n && delta == 0) {
          res.add(path.toString());
          return;
       }
       if (len == 2 * n || delta < 0) return;

       path.append("(");
       dfs(res, path, delta + 1, n);
       path.setLength(len);

       path.append(")");
       dfs(res, path, delta - 1, n);
       path.setLength(len);
}
