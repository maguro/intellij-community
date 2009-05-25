package com.intellij.codeInsight.intention.impl;

import com.intellij.application.options.editor.CodeFoldingConfigurable;
import com.intellij.application.options.editor.EditorOptions;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.application.ApplicationBundle;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * @author cdr
 */
public class EditFoldingOptionsAction implements IntentionAction {
  @NotNull
  public String getText() {
    return ApplicationBundle.message("edit.code.folding.options");
  }

  @NotNull
  public String getFamilyName() {
    return getText();
  }

  public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
    return editor.getFoldingModel().isOffsetCollapsed(editor.getCaretModel().getOffset());
  }

  public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
    EditorOptions editorOptions = ShowSettingsUtil.getInstance().findApplicationConfigurable(EditorOptions.class);
    final Configurable[] configurables = editorOptions.getConfigurables();
    for (Configurable c : configurables) {
      if (c instanceof CodeFoldingConfigurable) {
        ShowSettingsUtil.getInstance().editConfigurable(project, c);
        break;
      }
    }
  }

  public boolean startInWriteAction() {
    return false;
  }
}
