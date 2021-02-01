package com.company.testaddandcreatenew.web.screens.child;

import com.company.testaddandcreatenew.entity.Parent;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.testaddandcreatenew.entity.Child;

import javax.inject.Inject;

@UiController("testaddandcreatenew_Child.edit")
@UiDescriptor("child-edit.xml")
@EditedEntityContainer("childDc")
@LoadDataBeforeShow
public class ChildEdit extends StandardEditor<Child> {
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;

    @Subscribe("saveAndNew")
    public void onSaveAndNew(Action.ActionPerformedEvent event) {
        if (validateScreen().isEmpty()) {
            Parent parent = getEditedEntity().getParent();
            parent.getChildren().add(getEditedEntity());
            commitChanges()
                    .then(() -> {
                        commitActionPerformed = true;
                        if (showSaveNotification) {
                            Entity entity = getEditedEntity();
                            MetadataTools metadataTools = getBeanLocator().get(MetadataTools.NAME);
                            Messages messages = getBeanLocator().get(Messages.NAME);

                            notifications.create(Notifications.NotificationType.TRAY)
                                    .withCaption(messages.formatMainMessage("info.EntitySave",
                                            messages.getTools().getEntityCaption(entity.getMetaClass()),
                                            metadataTools.getInstanceName(entity)))
                                    .show();
                        }
                    });
            // create new item, set as edited entity
            Child newItem = dataContext.create(Child.class);
            newItem.setParent(parent);
            getEditedEntityContainer().setItem(newItem);
        }
    }
}