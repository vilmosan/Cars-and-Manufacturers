package hu.pte.ttk.vaadin.vaadindemo.menu;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import hu.pte.ttk.vaadin.vaadindemo.security.SecurityUtils;

public class MenuComponent extends HorizontalLayout {

	public MenuComponent() {
		Anchor mainLink = new Anchor();
		mainLink.setText("Main page");
		mainLink.setHref("/");
		add(mainLink);

		Anchor carLink = new Anchor();
		carLink.setText("List of Cars");
		carLink.setHref("/car");
		add(carLink);

		Anchor brandLink = new Anchor();
		brandLink.setText("List of Brands");
		brandLink.setHref("/brand");
		add(brandLink);

		if (SecurityUtils.isAdmin()) {
			Anchor userLink = new Anchor();
			userLink.setText("List of Users");
			userLink.setHref("/user");
			add(userLink);
		}

		Anchor logoutLink = new Anchor();
		logoutLink.setText("Log Out");
		logoutLink.setHref("/logout");
		add(logoutLink);
	}
}
