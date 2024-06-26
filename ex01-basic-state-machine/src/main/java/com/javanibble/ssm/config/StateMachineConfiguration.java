package com.javanibble.ssm.config;


import com.javanibble.ssm.domain.Events;
import com.javanibble.ssm.domain.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;


@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<States, Events> {


    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
            .withConfiguration()
            .autoStartup(true)
            .listener(listener());
    }


    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
            .withStates()
            .initial(States.INITIAL_STATE)
            .end(States.END_STATE)
            .states(EnumSet.allOf(States.class));
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
            .withExternal()
            .source(States.INITIAL_STATE).target(States.STATE_1).event(Events.EVENT_1)
            .and()
            .withExternal()
            .source(States.STATE_1).target(States.END_STATE).event(Events.EVENT_2);
    }


    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

}
