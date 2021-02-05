package com.company.testaddandcreatenew.web.screens.parent;

import com.company.testaddandcreatenew.entity.Child;
import com.company.testaddandcreatenew.web.screens.child.ChildEdit;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.LinkButton;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.icons.Icons;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.testaddandcreatenew.entity.Parent;

import javax.inject.Inject;

import java.util.List;

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
    @Inject
    private CollectionPropertyContainer<Child> childrenDc;

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

    @Install(to = "childrenTable.create", subject = "afterCloseHandler")
    private void childrenTableCreateAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        ChildEdit childEdit = ((ChildEdit) afterCloseEvent.getScreen());
        List<Child> additionalNewEntities = childEdit.getAdditionalNewEntities();

        if (isCommitCloseAction(afterCloseEvent)) {
            // closed with OK. Insert items to index before last.
            int indexToAdd = childrenDc.getMutableItems().size() - 1;
            if (indexToAdd < 0) {
                indexToAdd = 0;
            }

            for (Child child: additionalNewEntities) {
                childrenDc.getMutableItems().add(indexToAdd++, child);
            }
        } else {
            // closed with Cancel
            for (Child child: additionalNewEntities) {
                childrenDc.getMutableItems().add(child);
            }
        }
    }

    protected boolean isCommitCloseAction(AfterCloseEvent afterCloseEvent) {
        CloseAction closeAction = afterCloseEvent.getCloseAction();
        return (closeAction instanceof StandardCloseAction)
                && ((StandardCloseAction) closeAction).getActionId().equals(Window.COMMIT_ACTION_ID);
    }

    @Install(to = "childrenTable.create", subject = "screenConfigurer")
    private void childrenTableCreateScreenConfigurer(Screen screen) {
        ((ChildEdit) screen).setParentEntity(getEditedEntity());
    }
}