package com.jeremy.messenger.views.messenger;

import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@PWA(name = "Messenger", shortName = "Messenger", enableInstallPrompt = false)
@Theme(themeFolder = "messenger")
@PageTitle("Messenger")
@Route(value = "")
@CssImport("./styles/styles.css")
@Push
public class MessengerView extends VerticalLayout {

    private String username;
    
    private final Sinks.Many<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;
    public MessengerView(Sinks.Many<ChatMessage> publisher,
                        Flux<ChatMessage> messages) {
        this.publisher = publisher;
        this.messages = messages;
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");
        H1 header = new H1("Welcome to the chat");
        header.getElement().getThemeList().add("dark");
        add(header);

        askUsername();
    }

    private void askUsername() {
        HorizontalLayout layout = new HorizontalLayout();
        TextField usernameField = new TextField();
        Button startButton = new Button("Start chat");
        layout.add(usernameField, startButton);
        add(layout);

        startButton.addClickListener(click -> {
            username = usernameField.getValue();
            remove(layout);
            Tabs tabs = new Tabs(new Tab("#general"), new Tab("#Sports"), new Tab("#Video Games"));
            tabs.setWidthFull();
            add(tabs);
            showChat();
        });

    }

    private void showChat() {
        MessageList messageList = new MessageList();
        add(messageList, createInputLayout());
        expand(messageList);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        messages.subscribe(message -> MessengerView.this.getUI().ifPresent(ui -> ui.access(() ->
                    messageList.add(new Paragraph(message.getTime().format(formatter) + " - " + message.getFrom() + ": " + message.getMessage()))))
        );
    }

    private Component createInputLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");

        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(messageField, sendButton);
        layout.setWidth("100%");
        layout.expand(messageField);

        sendButton.addClickListener(click -> {
            publisher.tryEmitNext(new ChatMessage(username, messageField.getValue())).orThrow();
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        return layout;
    }
}
