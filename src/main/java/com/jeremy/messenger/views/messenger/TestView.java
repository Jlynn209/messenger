package com.jeremy.messenger.views.messenger;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.Component;

@Route("Test")
public class TestView extends VerticalLayout{

    public TestView(){
        H1 test = new H1("Hello world");
        add(test);
    }

}
