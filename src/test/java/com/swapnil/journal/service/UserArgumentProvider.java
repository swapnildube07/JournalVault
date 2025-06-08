    package com.swapnil.journal.service;

    import com.swapnil.journal.Entity.User;
    import org.junit.jupiter.api.extension.ExtensionContext;
    import org.junit.jupiter.params.provider.Arguments;
    import org.junit.jupiter.params.provider.ArgumentsProvider;

    import java.util.stream.Stream;

    public class UserArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(new User("shayam23", "shayam")),
                    Arguments.of(new User("sham23", "sham"))
            );
        }
    }