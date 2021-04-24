package hu.pte.ttk.vaadin.vaadindemo.security;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import hu.pte.ttk.vaadin.vaadindemo.menu.MenuComponent;

@Route("403")
public class ForbiddenView extends VerticalLayout {

    public ForbiddenView(){
        add(new MenuComponent());
        add("ACess Denied!");
    }
}
