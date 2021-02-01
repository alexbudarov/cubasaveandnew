package com.company.testaddandcreatenew.web.screens.parent;

import com.haulmont.cuba.gui.screen.*;
import com.company.testaddandcreatenew.entity.Parent;

@UiController("testaddandcreatenew_Parent.browse")
@UiDescriptor("parent-browse.xml")
@LookupComponent("parentsTable")
@LoadDataBeforeShow
public class ParentBrowse extends StandardLookup<Parent> {
}