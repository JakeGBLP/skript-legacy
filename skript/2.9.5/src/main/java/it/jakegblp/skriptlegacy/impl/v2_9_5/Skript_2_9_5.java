package it.jakegblp.skriptlegacy.impl.v2_9_5;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import it.jakegblp.skriptlegacy.api.GenericRelation;
import it.jakegblp.skriptlegacy.api.SkriptAdapter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.comparator.Comparators;
import org.skriptlang.skript.lang.comparator.Relation;
import org.skriptlang.skript.lang.converter.Converter;
import org.skriptlang.skript.lang.converter.Converters;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Skript_2_9_5 implements SkriptAdapter {

    @Override
    public boolean exactComparatorExists(Class<?> firstType, Class<?> secondType) {
        return Comparators.exactComparatorExists(firstType, secondType);
    }

    @Override
    public <T1, T2> void registerComparator(Class<T1> firstType, Class<T2> secondType, BiFunction<T1, T2, GenericRelation> comparator) {
        Comparators.registerComparator(firstType, secondType, (o1, o2) -> Relation.valueOf(comparator.apply(o1, o2).toString()));
    }

    @Override
    public <From, To> To[] convertUnsafe(From[] from, Class<?> toType, Function<? super From, ? extends To> converter) {
        return Converters.convertUnsafe(from, toType, (Converter<From, To>) converter::apply);
    }

    @Override
    public <F, T> void registerConverter(Class<F> from, Class<T> to, Function<F, T> converter) {
        Converters.registerConverter(from, to, converter::apply);
    }

    @Override
    public <T, E extends Event> void registerEventValue(Class<E> event, Class<T> type, Function<E, T> function, int time) {
        EventValues.registerEventValue(event, type, new Getter<>() {
            @Override
            public @Nullable T get(E arg) {
                return function.apply(arg);
            }
        }, time);
    }

    @Override
    public <T> boolean test(Expression<T> expr, Event event, Predicate<T> predicate) {
        return expr.check(event, predicate::test);
    }

    @Override
    public <T> boolean test(Expression<T> expr, Event event, Predicate<T> predicate, boolean isNegated) {
        return expr.check(event, predicate::test, isNegated);
    }

    @Override
    public long getTicks(Timespan timespan) {
        return timespan.getAs(Timespan.TimePeriod.TICK);
    }

    @Override
    public long getMillisecond(Timespan timespan) {
        return timespan.getAs(Timespan.TimePeriod.MILLISECOND);
    }

    @Override
    public Timespan fromTicks(long ticks) {
        return new Timespan(Timespan.TimePeriod.TICK,ticks);
    }
}
