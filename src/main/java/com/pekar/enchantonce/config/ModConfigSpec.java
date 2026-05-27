package com.pekar.enchantonce.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModConfigSpec
{
    private ModConfigSpec()
    {
    }

    public static class BooleanValue extends Definition<Boolean>
    {
        private BooleanValue(String name, boolean defaultValue)
        {
            super(name, defaultValue);
        }

        public static BooleanValue define(String name, boolean defaultValue)
        {
            return new BooleanValue(name, defaultValue);
        }

        public boolean isTrue()
        {
            return getValue();
        }

        public boolean isFalse()
        {
            return !getValue();
        }
    }

    public static class IntValue extends Definition<Integer>
    {
        private final int min;
        private final int max;

        private IntValue(String name, int defaultValue, int min, int max)
        {
            super(name, defaultValue);
            this.min = min;
            this.max = max;
        }

        public static IntValue define(String name, int defaultValue, int min, int max)
        {
            return new IntValue(name, defaultValue, min, max);
        }

        public int getAsInt()
        {
            return getValue();
        }

        @Override
        public void setValue(Integer value)
        {
            var val = Math.clamp(value, min, max);
            super.setValue(val);
        }
    }

    public static class Builder
    {
        private final List<String> comments = new ArrayList<>();

        public Builder comment(String... textLines)
        {
            comments.addAll(Arrays.asList(textLines));
            return this;
        }

        public BooleanValue define(String name, boolean defaultValue)
        {
            var definition = BooleanValue.define(name, defaultValue);
            for (var comment : comments)
            {
                definition.addComment(comment);
            }

            comments.clear();

            return definition;
        }

        public IntValue defineInRange(String name, int defaultValue, int min, int max)
        {
            var definition = IntValue.define(name, defaultValue, min, max);
            for (var comment : comments)
            {
                definition.addComment(comment);
            }

            comments.clear();

            return definition;
        }

        public ModConfigSpec build()
        {
            return new ModConfigSpec();
        }
    }
}
