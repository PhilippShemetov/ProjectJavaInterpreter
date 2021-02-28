/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.View;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.python.util.PythonInterpreter;
import server.Handler.IServerHandler;
import server.Model.IModelServer;

/**
 *
 * @author Vasilisa
 */
public class ViewServer implements IViewServer {

    IServerHandler srv;
    IModelServer m;

    ArrayList<String> codeDB = new ArrayList<>();

    ArrayList<Patch> patchDB = new ArrayList<>();

    ArrayList<List> changeCodeDB = new ArrayList<>();

    public ViewServer(IServerHandler _srv, IModelServer _m) {
        m = _m;
        srv = _srv; //Разный для каждого потока

        codeDB.add("");

        start();
        m.addUser(this, srv);
    }

    void start() {
        //Controller p = this;
        new Thread() {
            @Override
            public void run() {
                int code = -2;

                while (code != -1) {
                    code = srv.getCode();
                    switch (code) {
                        case 1:
                            m.setText(srv.getText(), srv);
                            break;
                        case 2:
                            m.setResult(resultOfInter());
                            break;
                        case 3:
                            codeDB.add(srv.getText());
                            System.out.println("codedb=" + codeDB.size());
                            Patch<String> diffa = diff(codeDB.get(codeDB.size() - 2), codeDB.get(codeDB.size() - 1));
                            patchDB.add(diffa);
                            List<String> lines1 = Arrays.asList(codeDB.get(codeDB.size() - 2).split("\\r?\\n"));
                            List<String> lines2 = Arrays.asList(codeDB.get(codeDB.size() - 1).split("\\r?\\n"));
                            List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("text1", "text2", lines1, diffa, 0);
                            changeCodeDB.add(lines2);
                            m.setChanges(String.join("\r\n", unifiedDiff));
                            break;
                        case 4:
                            if (patchDB.size() > 0) {
                                Patch<String> lastPatch = patchDB.get(patchDB.size() - 1);
                                System.out.println(changeCodeDB.size());
                                System.out.println("PATCH=" + (patchDB.size() - 1));
                                System.out.println(changeCodeDB.get((patchDB.size() - 1)));
                                List<String> originalText = DiffUtils.unpatch(changeCodeDB.get((patchDB.size() - 1)), lastPatch);
                                m.setCodeToUnpatch(String.join("\r\n", originalText));
                                changeCodeDB.remove(changeCodeDB.size() - 1);
                                codeDB.remove(codeDB.size() - 1);
                                patchDB.remove(patchDB.size() - 1);
                                
                            }

                    }

                }
            }
        }.start();

    }

    @Override
    public IServerHandler getServerHandler() {
        return this.srv;
    }

    @Override
    public void send() {
        srv.setData(m.getText(), 1);
    }

    @Override
    public void sendResult() {
        srv.setData(m.getResult(), 2);
    }

    @Override
    public void sendChangeCode() {
        srv.setData(m.getChangesCode(), 3);
    }
    
    @Override
    public void sendOriginalText() {
        srv.setData(m.getText(), 4);
    }

    private String resultOfInter() {
        try (PythonInterpreter pyInterp = new PythonInterpreter()) {
            StringWriter output = new StringWriter();
            StringWriter err = new StringWriter();
            pyInterp.setOut(output);
            pyInterp.exec(srv.getText());
            return output.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    private static Patch<String> diff(String text1, String text2) {
        List<String> lines1 = Arrays.asList(text1.split("\\r?\\n"));
        List<String> lines2 = Arrays.asList(text2.split("\\r?\\n"));
        Patch<String> diff = DiffUtils.diff(lines1, lines2);
        return diff;
    }

}
