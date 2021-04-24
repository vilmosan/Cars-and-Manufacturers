package hu.pte.ttk.vaadin.vaadindemo.menu;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import hu.pte.ttk.vaadin.vaadindemo.security.SecurityUtils;

public class MenuComponent extends HorizontalLayout {

    public MenuComponent(){
        Anchor mainLink = new Anchor();
        mainLink.setText("Main page");
        mainLink.setHref("/");
        add(mainLink);

        Anchor bookLink = new Anchor();
        bookLink.setText("List of Cars");
        bookLink.setHref("/car");
        add(bookLink);

        if(SecurityUtils.isAdmin()){
            Anchor authorLink = new Anchor();
            authorLink.setText("List of Brands");
            authorLink.setHref("/brand");
            add(authorLink);
        }
    }
}
