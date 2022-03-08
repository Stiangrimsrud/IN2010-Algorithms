class Fibonacci {
    public static void main(String[] args){
        Fibonacci f = new Fibonacci();
        long argument = Integer.parseInt(args[0]);
        if(argument < 0) argument = 0;
        System.out.println("f(" + argument + ") = " + f.fibonacci(argument));
    }

    long fibonacci(long n){
        if(n <= 1){
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }

    }
}