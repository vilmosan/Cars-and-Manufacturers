package hu.pte.ttk.vaadin.vaadindemo;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import hu.pte.ttk.vaadin.vaadindemo.menu.MenuComponent;

@Route
public class MainView extends VerticalLayout {

	public MainView() {
		add(new MenuComponent());
		Text text = new Text("Main Page");
		add(text);
	}
}
