package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.ReportService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    private ReportService service;

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();

        //メソッドを実行
        invoke();

        service.close();

    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

      //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中の従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<ReportView> reports = service.getMinePerPage(loginEmployee, page);

        //ログイン中の従業員が作成した日報データの件数を取得
        long myReportsCount = service.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT, myReportsCount); //ログイン中の従業員が作成した日報の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //↑ここまでで、Topにログイン従業員の日報を表示するための情報をリクエストスコープをに納めた。

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}