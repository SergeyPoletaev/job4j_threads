package ru.job4j.nba.cache;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class CacheTest {

    @Test
    public void whenUpdateIsSuccessfulThenResultIsTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, new AtomicInteger(0));
        cache.add(model);
        model.setName("User 1");
        assertThat(cache.update(model)).isTrue();
    }

    @Test
    public void whenUpdateIsFailsThenResultIsFalse() {
        Cache cache = new Cache();
        cache.add(new Base(1, new AtomicInteger(0)));
        Base model = new Base(2, new AtomicInteger(0));
        model.setName("User 2");
        assertThat(cache.update(model)).isFalse();
    }

    @Test
    public void whenVersionsAreDifferentThenUpdateResultIsEx() {
        Cache cache = new Cache();
        cache.add(new Base(1, new AtomicInteger(0)));
        Base model = new Base(1, new AtomicInteger(1));
        model.setName("User 1");
        Throwable thrown = catchThrowable(() -> cache.update(model));
        assertThat(thrown).isInstanceOf(OptimisticException.class);
        assertThat(thrown.getMessage()).isEqualTo("Versions are not equal");
    }

    @Test
    public void whenValuesAreTheSameThenTheUpdateResultIsTrue() {
        Cache cache = new Cache();
        cache.add(new Base(1, new AtomicInteger(0)));
        Base model = new Base(1, new AtomicInteger(0));
        assertThat(cache.update(model)).isTrue();
    }

    @Test
    public void delete() {
        Cache cache = new Cache();
        Base model1 = new Base(1, new AtomicInteger(0));
        Base model2 = new Base(2, new AtomicInteger(0));
        cache.add(model1);
        cache.add(model2);
        cache.delete(model1);
        assertThat(cache.update(model1)).isFalse();
    }
}