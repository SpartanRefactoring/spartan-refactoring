package il.org.spartan.Leonidas.plugin.GUI.ViewAvailableTippersInFile;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Amir on 20-06-2017.
 */
class TippersViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        if (psiClass != null)
			new TippersView(psiClass.getContainingFile());
    }

        @Nullable
        private PsiElement getPsiElementFromContext(AnActionEvent e) {
            PsiFile $ = e.getData(LangDataKeys.PSI_FILE);
            Editor editor = e.getData(PlatformDataKeys.EDITOR);
            return $ == null || editor == null ? null : $.findElementAt(editor.getCaretModel().getOffset());
        }


        private PsiClass getPsiClassFromContext(AnActionEvent ¢) {
            return PsiTreeUtil.getParentOfType(getPsiElementFromContext(¢), PsiClass.class);
        }
}