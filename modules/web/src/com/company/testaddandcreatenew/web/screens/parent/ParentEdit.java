package com.company.testaddandcreatenew.web.screens.parent;

import com.company.testaddandcreatenew.entity.Child;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.LinkButton;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.icons.Icons;
import com.haulmont.cuba.gui.screen.*;
import com.company.testaddandcreatenew.entity.Parent;

import javax.inject.Inject;

import static com.google.common.base.Strings.isNullOrEmpty;

@UiController("testaddandcreatenew_Parent.edit")
@UiDescriptor("parent-edit.xml")
@EditedEntityContainer("parentDc")
@LoadDataBeforeShow
public class ParentEdit extends StandardEditor<Parent> {
    @Inject
    private UiComponents uiComponents;
    @Inject
    private Table<Child> childrenTable;

    @Subscribe
    public void init(InitEvent event) {
        childrenTable.addGeneratedColumn("instructies", p -> {
            if (!isNullOrEmpty(p.getName())) {
                Icons.Icon icon = CubaIcon.GLOBE;
                LinkButton linkButton = uiComponents.create(LinkButton.class);
                linkButton.setIcon(CubaIcon.EXCLAMATION_TRIANGLE.source());
                linkButton.setDescriptionAsHtml(true);
                linkButton.setDescription(p.getName());
                return linkButton;
            } else {
                return null;
            }
        });
    }
}