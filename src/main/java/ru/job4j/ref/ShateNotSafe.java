package ru.job4j.ref;

public class ShateNotSafe {

    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                }
        );
        first.start();
        first.join();
        System.out.println(user.getName());
        System.out.println(cache.findById(1).getName());
    }
}
