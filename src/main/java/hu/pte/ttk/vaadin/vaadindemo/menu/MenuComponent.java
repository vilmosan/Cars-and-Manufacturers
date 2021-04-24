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
        bookLink.setText("Book page");
        bookLink.setHref("/book");
        add(bookLink);

        if(SecurityUtils.isAdmin()){
            Anchor authorLink = new Anchor();
            authorLink.setText("Author page");
            authorLink.setHref("/author");
            add(authorLink);
        }
    }
}
