package gwt.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface MenuView<T> extends IsWidget {

    public interface Presenter {      
        void goToJournalType();
        void goToDocType();
        void goToAccGrp();
        void goToAccChart();
        void goToBeginning();
        void goToFin();
        
        void goToJournal(String keyString);
        
        void goToAccChartRep();
        void goToJournalRep(String journalTypeKeyString, int beginDay,
                int beginMonth, int beginYear, int endDay, int endMonth,
                int endYear);
        void goToLedgerRep(String beginAccChartKeyString,
                String endAccChartKeyString, int beginDay, int beginMonth,
                int beginYear, int endDay, int endMonth, int endYear,
                boolean doShowAll);
        void goToTrialRep(boolean doShowAll);
        void goToBalanceRep(String assetACKeyString, String debtACKeyString,
                String shareholderACKeyString, String accruedProfitACKeyString,
                String incomeACKeyString, String expenseACKeyString,
                boolean doShowAll, boolean doesSplit);
        void goToProfitRep(String incomeACKeyString,
                String expenseACKeyString, boolean doShowAll, boolean doesSplit);
        void goToCostRep(String costACKeyString, boolean doShowAll);
        void goToWorkSheet(String assetACKeyString, String debtACKeyString,
                String shareholderACKeyString, String accruedProfitACKeyString,
                String incomeACKeyString, String expenseACKeyString,
                String costACKeyString, boolean doShowAll);
        void goToFinRep(String finKeyString);
    }

    Widget asWidget();
    void init(Presenter presenter);
    void setMenu(T t, String action);
}
