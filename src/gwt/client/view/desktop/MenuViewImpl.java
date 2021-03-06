package gwt.client.view.desktop;

import gwt.client.TCF;
import gwt.client.TConstants;
import gwt.client.def.FisDef;
import gwt.client.place.AllPlace;
import gwt.client.ui.CustomIntBox;
import gwt.client.ui.CustomListBox;
import gwt.client.ui.CustomSuggestBox;
import gwt.client.view.MenuView;
import gwt.shared.InvalidValueException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MenuViewImpl<T> extends Composite implements MenuView<T> {

    enum ReportSubMenuState {
        NONE,
        JOURNAL,
        LEDGER,
        TRIAL,
        BALANCE,
        PROFIT,
        COST,
        WORK_SHEET
    }

    @SuppressWarnings("rawtypes")
    @UiTemplate("MenuViewImpl.ui.xml")
    interface MyUiBinder extends UiBinder<Widget, MenuViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    interface Style extends CssResource {
        String btn();
        String clickedBtn();
        String suggestBox();
    }

    @UiField
    Style style;
    
    // Setups
    @UiField
    FlowPanel setupPanel;
    @UiField
    Button journalTypeBtn;
    @UiField
    Button docTypeBtn;
    @UiField
    Button accGrpBtn;
    @UiField
    Button accChartBtn;
    @UiField
    Button beginningBtn;
    @UiField
    Button finBtn;

    // Journals
    @UiField
    FlowPanel journalPanel;

    // Reports
    @UiField
    FlowPanel reportPanel;
    @UiField
    Button accChartRepBtn;
    @UiField
    Button journalRepBtn;
    @UiField
    Button ledgerRepBtn;
    @UiField
    Button trialRepBtn;
    @UiField
    Button balanceRepBtn;
    @UiField
    Button profitRepBtn;
    @UiField
    Button costRepBtn;
    @UiField
    Button workSheetBtn;
    @UiField
    FlowPanel finRepPanel;
    
    // Sub menu
    @UiField
    FlowPanel subMenuPanel;
    @UiField
    FlowPanel journalTypePanel;
    @UiField
    Label journalTypeLb;
    @UiField
    CustomListBox journalTypeLB;
    @UiField
    FlowPanel accNoPanel;
    @UiField
    Label beginAccNoLb;
    @UiField
    CustomSuggestBox beginAccNoSB;
    @UiField
    Label errBeginAccNoLb;
    @UiField
    Label endAccNoLb;
    @UiField
    CustomSuggestBox endAccNoSB;
    @UiField
    Label errEndAccNoLb;
    @UiField
    FlowPanel datePanel;
    @UiField
    Label beginDateLb;
    @UiField
    Label beginDayLb;
    @UiField
    CustomIntBox beginDayIB;
    @UiField
    Label beginMonthLb;
    @UiField
    CustomIntBox beginMonthIB;
    @UiField
    Label beginYearLb;
    @UiField
    CustomIntBox beginYearIB;
    @UiField
    Label errBeginDateLb;
    @UiField
    Label endDateLb;
    @UiField
    Label endDayLb;
    @UiField
    CustomIntBox endDayIB;
    @UiField
    Label endMonthLb;
    @UiField
    CustomIntBox endMonthIB;
    @UiField
    Label endYearLb;
    @UiField
    CustomIntBox endYearIB;
    @UiField
    Label errEndDateLb;
    @UiField
    FlowPanel assetPanel;
    @UiField
    Label assetAccNoLb;
    @UiField
    CustomSuggestBox assetAccNoSB;
    @UiField
    Label errAssetLb;
    @UiField
    FlowPanel debtPanel;
    @UiField
    Label debtAccNoLb;
    @UiField
    CustomSuggestBox debtAccNoSB;
    @UiField
    Label errDebtLb;
    @UiField
    FlowPanel shareholderPanel;
    @UiField
    Label shareholderAccNoLb;
    @UiField
    CustomSuggestBox shareholderAccNoSB;
    @UiField
    Label errShareholderLb;
    @UiField
    Label accruedProfitAccNoLb;
    @UiField
    CustomSuggestBox accruedProfitAccNoSB;
    @UiField
    Label errAccruedProfitLb;
    @UiField
    FlowPanel incomePanel;
    @UiField
    Label incomeAccNoLb;
    @UiField
    CustomSuggestBox incomeAccNoSB;
    @UiField
    Label errIncomeLb;
    @UiField
    FlowPanel expensePanel;
    @UiField
    Label expenseAccNoLb;
    @UiField
    CustomSuggestBox expenseAccNoSB;
    @UiField
    Label errExpenseLb;
    @UiField
    FlowPanel costPanel;
    @UiField
    Label costAccNoLb;
    @UiField
    CustomSuggestBox costAccNoSB;
    @UiField
    Label errCostLb;
    @UiField
    FlowPanel showAllPanel;
    @UiField
    CheckBox showAllCB;
    @UiField
    FlowPanel doesSplitPanel;
    @UiField
    CheckBox doesSplitCB;
    @UiField
    Button okBtn;

    private static final TConstants constants = TCF.get();
    private Presenter presenter;
    private FisDef<T> fisDef;
    private ReportSubMenuState reportSubMenuState;

    public MenuViewImpl(FisDef<T> fisDef) {
        this.fisDef = fisDef;
        this.reportSubMenuState = ReportSubMenuState.NONE;
        initWidget(uiBinder.createAndBindUi(this));

        // Setups
        journalTypeBtn.setText(constants.setup() + constants.journalType());
        journalTypeBtn.addStyleName(style.btn());
        docTypeBtn.setText(constants.setup() + constants.docType());
        docTypeBtn.addStyleName(style.btn());
        accGrpBtn.setText(constants.setup() + constants.accGrp());
        accGrpBtn.addStyleName(style.btn());
        accChartBtn.setText(constants.setup() + constants.accChart());
        accChartBtn.addStyleName(style.btn());
        beginningBtn.setText(constants.setup() + constants.beginning());
        beginningBtn.addStyleName(style.btn());
        finBtn.setText(constants.setup() + constants.fin());
        finBtn.addStyleName(style.btn());

        // Reports
        accChartRepBtn.setText(constants.accChart());
        accChartRepBtn.addStyleName(style.btn());
        journalRepBtn.setText(constants.journalType());
        journalRepBtn.addStyleName(style.btn());
        ledgerRepBtn.setText(constants.ledger());
        ledgerRepBtn.addStyleName(style.btn());
        trialRepBtn.setText(constants.trial());
        trialRepBtn.addStyleName(style.btn());
        balanceRepBtn.setText(constants.balanceSheet());
        balanceRepBtn.addStyleName(style.btn());
        profitRepBtn.setText(constants.profitReport());
        profitRepBtn.addStyleName(style.btn());
        costRepBtn.setText(constants.costReport());
        costRepBtn.addStyleName(style.btn());

        workSheetBtn.setVisible(false);
        //workSheetBtn.setText(constants.workSheet());
        //workSheetBtn.addStyleName(style.btn());
        
        // Sub menu
        journalTypeLb.setText(constants.journalType());

        beginAccNoLb.setText(constants.accNo());
        beginAccNoSB.addTextBoxStyleName(style.suggestBox());
        endAccNoLb.setText(constants.end());
        endAccNoSB.addTextBoxStyleName(style.suggestBox());
        
        beginDateLb.setText(constants.begin());
        beginDayLb.setText(constants.day());
        beginMonthLb.setText(constants.month());
        beginYearLb.setText(constants.year());
        endDateLb.setText(constants.end());
        endDayLb.setText(constants.day());
        endMonthLb.setText(constants.month());
        endYearLb.setText(constants.year());

        assetAccNoLb.setText(constants.assetAccNo());
        assetAccNoSB.addTextBoxStyleName(style.suggestBox());
        debtAccNoLb.setText(constants.debtAccNo());
        debtAccNoSB.addTextBoxStyleName(style.suggestBox());
        shareholderAccNoLb.setText(constants.shareholderAccNo());
        shareholderAccNoSB.addTextBoxStyleName(style.suggestBox());
        accruedProfitAccNoLb.setText(constants.accruedProfit());
        accruedProfitAccNoSB.addTextBoxStyleName(style.suggestBox());
        incomeAccNoLb.setText(constants.incomeAccNo());
        incomeAccNoSB.addTextBoxStyleName(style.suggestBox());
        expenseAccNoLb.setText(constants.expenseAccNo());
        expenseAccNoSB.addTextBoxStyleName(style.suggestBox());
        costAccNoLb.setText(constants.costAccNo());
        costAccNoSB.addTextBoxStyleName(style.suggestBox());
        
        showAllCB.setText(constants.showAll());
        doesSplitCB.setText(constants.doesSplit());

        okBtn.setText(constants.ok());
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void init(Presenter presenter) {
        this.presenter = presenter;

        setupPanel.setVisible(false);
        journalPanel.setVisible(false);
        reportPanel.setVisible(false);
        subMenuPanel.setVisible(false);
    }

    @Override
    public void setMenu(T t, String action) {

        if (action.equals(AllPlace.SETUP)) {
            setupPanel.setVisible(true);
        } else if (action.equals(AllPlace.JOUR)) {
            journalPanel.clear();
            for (int i = 0; i < fisDef.getJTListSize(t); i++) {
                final String keyString = fisDef.getJTKeyString(t, i);
                Button btn = new Button(fisDef.getJTShortName(t, i));
                btn.addStyleName(style.btn());
                btn.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        presenter.goToJournal(keyString);
                    }
                });
                journalPanel.add(btn);
            }

            journalPanel.setVisible(true);
        } else if (action.equals(AllPlace.REPORT)) {
            finRepPanel.clear();
            for (int i = 0; i < fisDef.getFinHeaderListSize(t); i++) {
                final String keyString = fisDef.getFinHeaderKeyString(t, i);
                Button btn = new Button(fisDef.getFinHeaderName(t, keyString));
                btn.addStyleName(style.btn());
                btn.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        reportSubMenuState = ReportSubMenuState.NONE;
                        presenter.goToFinRep(keyString);
                    }
                });
                finRepPanel.add(btn);
            }

            journalTypeLB.clear();
            for (int i = 0; i < fisDef.getJTListSize(t); i++) {
                journalTypeLB.addItem(fisDef.getJTShortName(t, i),
                        fisDef.getJTKeyString(t, i));
            }

            // Try to suggest acc no. for asset, debt, shareholder, income,
            //     expense, and cost
            String assetDefaultValue = null;
            String debtDefaultValue = null;
            String shareholderDefaultValue = null;
            String accruedProfitDefaultValue = null;
            String incomeDefaultValue = null;
            String expenseDefaultValue = null;
            String costDefaultValue = null;

            beginAccNoSB.clear();
            endAccNoSB.clear();
            assetAccNoSB.clear();
            debtAccNoSB.clear();
            shareholderAccNoSB.clear();
            accruedProfitAccNoSB.clear();
            incomeAccNoSB.clear();
            expenseAccNoSB.clear();
            costAccNoSB.clear();
            for (int i = 0; i < fisDef.getACListSize(t); i++) {
                String aCKeyString = fisDef.getACKeyString(t, i);
                String aCNo = fisDef.getACNo(t, i);
                String aCNoAndName = aCNo + " - " + fisDef.getACName(t, i);
                if (fisDef.getACIsControl(t, i)) {
                    assetAccNoSB.add(aCKeyString, aCNoAndName);
                    debtAccNoSB.add(aCKeyString, aCNoAndName);
                    shareholderAccNoSB.add(aCKeyString, aCNoAndName);
                    incomeAccNoSB.add(aCKeyString, aCNoAndName);
                    expenseAccNoSB.add(aCKeyString, aCNoAndName);
                    costAccNoSB.add(aCKeyString, aCNoAndName);
                    
                    if (aCNo.equals("10-00-00-00")) {
                        assetDefaultValue = aCNoAndName;
                    } else if (aCNo.equals("20-00-00-00")) {
                        debtDefaultValue = aCNoAndName;
                    } else if (aCNo.equals("30-00-00-00")) {
                        shareholderDefaultValue = aCNoAndName;
                    } else if (aCNo.equals("40-00-00-00")) {
                        incomeDefaultValue = aCNoAndName;
                    } else if (aCNo.equals("50-00-00-00")) {
                        expenseDefaultValue = aCNoAndName;
                    } else if (aCNo.equals("51-00-00-00")) {
                        costDefaultValue = aCNoAndName;
                    }
                } else if (fisDef.getACIsEntry(t, i)) {
                    beginAccNoSB.add(aCKeyString, aCNoAndName);
                    endAccNoSB.add(aCKeyString, aCNoAndName);
                    
                    accruedProfitAccNoSB.add(aCKeyString, aCNoAndName);
                    
                    if (aCNo.equals("32-00-00-00")) {
                        accruedProfitDefaultValue = aCNoAndName;
                    }
                } else {
                    throw new AssertionError();
                }
            }

            assetAccNoSB.setValue(assetDefaultValue);
            debtAccNoSB.setValue(debtDefaultValue);
            shareholderAccNoSB.setValue(shareholderDefaultValue);
            accruedProfitAccNoSB.setValue(accruedProfitDefaultValue);
            incomeAccNoSB.setValue(incomeDefaultValue);
            expenseAccNoSB.setValue(expenseDefaultValue);
            costAccNoSB.setValue(costDefaultValue);

            reportPanel.setVisible(true);
            updateReportSubMenu();
        } else {
            throw new AssertionError(action);
        }
    }

    @UiHandler("journalTypeBtn")
    void onJournalTypeBtnClicked(ClickEvent event) {
        presenter.goToJournalType();
    }

    @UiHandler("docTypeBtn")
    void onDocTypeBtnClicked(ClickEvent event) {
        presenter.goToDocType();
    }

    @UiHandler("accGrpBtn")
    void onAccGrpBtnClicked(ClickEvent event) {
        presenter.goToAccGrp();
    }

    @UiHandler("accChartBtn")
    void onAccChartBtnClicked(ClickEvent event) {
        presenter.goToAccChart();
    }

    @UiHandler("beginningBtn")
    void onBeginningBtnClicked(ClickEvent event) {
        presenter.goToBeginning();
    }

    @UiHandler("finBtn")
    void onFinanceBtnClicked(ClickEvent event) {
        presenter.goToFin();
    }

    @UiHandler("accChartRepBtn")
    void onAccChartRepBtnClicked(ClickEvent event) {
        reportSubMenuState = ReportSubMenuState.NONE;
        presenter.goToAccChartRep();
    }

    @UiHandler("journalRepBtn")
    void onJournalRepBtnClicked(ClickEvent event) {
        reportSubMenuState = ReportSubMenuState.JOURNAL;
        updateReportSubMenu();
    }

    @UiHandler("ledgerRepBtn")
    void onLedgerRepBtnClicked(ClickEvent event) {
        reportSubMenuState = ReportSubMenuState.LEDGER;
        updateReportSubMenu();
    }

    @UiHandler("trialRepBtn")
    void onTrialRepBtnClicked(ClickEvent event) {
        reportSubMenuState = ReportSubMenuState.TRIAL;
        updateReportSubMenu();
    }
    
    @UiHandler("balanceRepBtn")
    void onBalanceRepBtnClicked(ClickEvent event) {
        reportSubMenuState = ReportSubMenuState.BALANCE;
        updateReportSubMenu();
    }
    
    @UiHandler("profitRepBtn")
    void onProfitRepBtnClicked(ClickEvent event) {
        reportSubMenuState = ReportSubMenuState.PROFIT;
        updateReportSubMenu();
    }
    
    @UiHandler("costRepBtn")
    void onCostRepBtnClicked(ClickEvent event) {
        reportSubMenuState = ReportSubMenuState.COST;
        updateReportSubMenu();
    }
    
    @UiHandler("workSheetBtn")
    void onWorkSheetBtnClicked(ClickEvent event) {
        reportSubMenuState = ReportSubMenuState.WORK_SHEET;
        updateReportSubMenu();
    }
    
    private void updateReportSubMenu() {
        
        journalRepBtn.removeStyleName(style.clickedBtn());
        ledgerRepBtn.removeStyleName(style.clickedBtn());
        trialRepBtn.removeStyleName(style.clickedBtn());
        balanceRepBtn.removeStyleName(style.clickedBtn());
        profitRepBtn.removeStyleName(style.clickedBtn());
        costRepBtn.removeStyleName(style.clickedBtn());
        workSheetBtn.removeStyleName(style.clickedBtn());
        
        if (reportSubMenuState == ReportSubMenuState.NONE) {
        } else if (reportSubMenuState == ReportSubMenuState.JOURNAL) {
            journalRepBtn.addStyleName(style.clickedBtn());

            journalTypePanel.setVisible(true);
            accNoPanel.setVisible(false);
            datePanel.setVisible(true);

            assetPanel.setVisible(false);
            debtPanel.setVisible(false);
            shareholderPanel.setVisible(false);
            incomePanel.setVisible(false);
            expensePanel.setVisible(false);
            costPanel.setVisible(false);
            
            showAllPanel.setVisible(false);
            doesSplitPanel.setVisible(false);
        } else if (reportSubMenuState == ReportSubMenuState.LEDGER) {
            ledgerRepBtn.addStyleName(style.clickedBtn());
            
            journalTypePanel.setVisible(false);
            accNoPanel.setVisible(true);
            datePanel.setVisible(true);

            assetPanel.setVisible(false);
            debtPanel.setVisible(false);
            shareholderPanel.setVisible(false);
            incomePanel.setVisible(false);
            expensePanel.setVisible(false);
            costPanel.setVisible(false);
            
            showAllPanel.setVisible(true);
            doesSplitPanel.setVisible(false);
        } else if (reportSubMenuState == ReportSubMenuState.TRIAL) {
            trialRepBtn.addStyleName(style.clickedBtn());
            
            journalTypePanel.setVisible(false);
            accNoPanel.setVisible(false);
            datePanel.setVisible(false);

            assetPanel.setVisible(false);
            debtPanel.setVisible(false);
            shareholderPanel.setVisible(false);
            incomePanel.setVisible(false);
            expensePanel.setVisible(false);
            costPanel.setVisible(false);
            
            showAllPanel.setVisible(true);
            doesSplitPanel.setVisible(false);
        } else if (reportSubMenuState == ReportSubMenuState.BALANCE) {
            balanceRepBtn.addStyleName(style.clickedBtn());
            
            journalTypePanel.setVisible(false);
            accNoPanel.setVisible(false);
            datePanel.setVisible(false);

            assetPanel.setVisible(true);
            debtPanel.setVisible(true);
            shareholderPanel.setVisible(true);
            incomePanel.setVisible(true);
            expensePanel.setVisible(true);
            costPanel.setVisible(false);
            
            showAllPanel.setVisible(true);
            doesSplitPanel.setVisible(true);
        } else if (reportSubMenuState == ReportSubMenuState.PROFIT) {
            profitRepBtn.addStyleName(style.clickedBtn());
            
            journalTypePanel.setVisible(false);
            accNoPanel.setVisible(false);
            datePanel.setVisible(false);

            assetPanel.setVisible(false);
            debtPanel.setVisible(false);
            shareholderPanel.setVisible(false);
            incomePanel.setVisible(true);
            expensePanel.setVisible(true);
            costPanel.setVisible(false);
            
            showAllPanel.setVisible(true);
            doesSplitPanel.setVisible(true);
        } else if (reportSubMenuState == ReportSubMenuState.COST) {
            costRepBtn.addStyleName(style.clickedBtn());
            
            journalTypePanel.setVisible(false);
            accNoPanel.setVisible(false);
            datePanel.setVisible(false);

            assetPanel.setVisible(false);
            debtPanel.setVisible(false);
            shareholderPanel.setVisible(false);
            incomePanel.setVisible(false);
            expensePanel.setVisible(false);
            costPanel.setVisible(true);
            
            showAllPanel.setVisible(true);
            doesSplitPanel.setVisible(false);
        } else if (reportSubMenuState == ReportSubMenuState.WORK_SHEET) {
            workSheetBtn.addStyleName(style.clickedBtn());
            
            journalTypePanel.setVisible(false);
            accNoPanel.setVisible(false);
            datePanel.setVisible(false);

            assetPanel.setVisible(true);
            debtPanel.setVisible(true);
            shareholderPanel.setVisible(true);
            incomePanel.setVisible(true);
            expensePanel.setVisible(true);
            costPanel.setVisible(true);

            showAllPanel.setVisible(true);
            doesSplitPanel.setVisible(false);
        } else {
            throw new AssertionError(reportSubMenuState);
        }

        subMenuPanel.setVisible(
                reportSubMenuState != ReportSubMenuState.NONE);
    }

    @UiHandler("okBtn")
    void onOkBtnClicked(ClickEvent event) {
        if (reportSubMenuState == ReportSubMenuState.JOURNAL) {
            if (!isBeginDateAndEndDateValid()) {
                return;
            }
            presenter.goToJournalRep(journalTypeLB.getValue(), beginDayIB.getCustomValue(), beginMonthIB.getCustomValue(),
                    beginYearIB.getCustomValue(), endDayIB.getCustomValue(), endMonthIB.getCustomValue(), endYearIB.getCustomValue());
        } else if (reportSubMenuState == ReportSubMenuState.LEDGER) {
            if (!isBeginDateAndEndDateValid()) {
                return;
            }

            String beginAccChartKeyString = null;
            errBeginAccNoLb.setText("");
            try {
                beginAccChartKeyString = beginAccNoSB.getKey();
            } catch (InvalidValueException e) {
                errBeginAccNoLb.setText(constants.invalidMsg());
                return;
            }

            String endAccChartKeyString = null;
            errEndAccNoLb.setText("");
            try {
                endAccChartKeyString = endAccNoSB.getKey();
            } catch (InvalidValueException e) {
                errEndAccNoLb.setText(constants.invalidMsg());
                return;
            }

            if (beginAccChartKeyString == null && endAccChartKeyString == null) {
                beginAccChartKeyString = AllPlace.FIRST;
                endAccChartKeyString = AllPlace.LAST;
            } else if (beginAccChartKeyString == null && endAccChartKeyString != null) {
                beginAccChartKeyString = AllPlace.FIRST;
            } else if (beginAccChartKeyString != null && endAccChartKeyString == null) {
                endAccChartKeyString = AllPlace.LAST;
            }

            boolean doShowAll = showAllCB.getValue();

            presenter.goToLedgerRep(beginAccChartKeyString, endAccChartKeyString,
                    beginDayIB.getCustomValue(), beginMonthIB.getCustomValue(),
                    beginYearIB.getCustomValue(), endDayIB.getCustomValue(),
                    endMonthIB.getCustomValue(), endYearIB.getCustomValue(),
                    doShowAll);
        } else if (reportSubMenuState == ReportSubMenuState.TRIAL) {
            boolean doShowAll = showAllCB.getValue();
            presenter.goToTrialRep(doShowAll);
        } else if (reportSubMenuState == ReportSubMenuState.BALANCE) {
            String assetACKeyString = getACKeyString(assetAccNoSB, assetAccNoLb);
            String debtACKeyString = getACKeyString(debtAccNoSB, debtAccNoLb);
            String shareholderACKeyString = getACKeyString(shareholderAccNoSB,
                    shareholderAccNoLb);
            String accruedProfitACKeyString = getACKeyString(accruedProfitAccNoSB,
                    accruedProfitAccNoLb);
            String incomeACKeyString = getACKeyString(incomeAccNoSB, incomeAccNoLb);
            String expenseACKeyString = getACKeyString(expenseAccNoSB, expenseAccNoLb);
            
            if (assetACKeyString == null || debtACKeyString == null
                    || shareholderACKeyString == null
                    || accruedProfitACKeyString == null || incomeACKeyString == null
                    || expenseACKeyString == null) {
                return;
            }
            
            boolean doShowAll = showAllCB.getValue();
            boolean doesSplit = doesSplitCB.getValue();

            presenter.goToBalanceRep(assetACKeyString, debtACKeyString,
                    shareholderACKeyString, accruedProfitACKeyString,
                    incomeACKeyString, expenseACKeyString, doShowAll, doesSplit);
        } else if (reportSubMenuState == ReportSubMenuState.PROFIT) {
            String incomeACKeyString = getACKeyString(incomeAccNoSB, incomeAccNoLb);
            String expenseACKeyString = getACKeyString(expenseAccNoSB, expenseAccNoLb);
            
            if (incomeACKeyString == null
                    || expenseACKeyString == null) {
                return;
            }
            
            boolean doShowAll = showAllCB.getValue();
            boolean doesSplit = doesSplitCB.getValue();

            presenter.goToProfitRep(incomeACKeyString,
                    expenseACKeyString, doShowAll, doesSplit);
        } else if (reportSubMenuState == ReportSubMenuState.COST) {
            String costACKeyString = getACKeyString(costAccNoSB, costAccNoLb);
            if (costACKeyString == null) {
                return;
            }
            
            boolean doShowAll = showAllCB.getValue();

            presenter.goToCostRep(costACKeyString, doShowAll);
        } else if (reportSubMenuState == ReportSubMenuState.WORK_SHEET) {
            String assetACKeyString = getACKeyString(assetAccNoSB, assetAccNoLb);
            String debtACKeyString = getACKeyString(debtAccNoSB, debtAccNoLb);
            String shareholderACKeyString = getACKeyString(shareholderAccNoSB,
                    shareholderAccNoLb);
            String accruedProfitACKeyString = getACKeyString(accruedProfitAccNoSB,
                    accruedProfitAccNoLb);
            String incomeACKeyString = getACKeyString(incomeAccNoSB, incomeAccNoLb);
            String expenseACKeyString = getACKeyString(expenseAccNoSB, expenseAccNoLb);
            String costACKeyString = getACKeyString(costAccNoSB, costAccNoLb);

            if (assetACKeyString == null || debtACKeyString == null
                    || shareholderACKeyString == null
                    || accruedProfitACKeyString == null
                    || incomeACKeyString == null
                    || expenseACKeyString == null || costACKeyString == null) {
                return;
            }
            
            boolean doShowAll = showAllCB.getValue();

            presenter.goToWorkSheet(assetACKeyString, debtACKeyString,
                    shareholderACKeyString, accruedProfitACKeyString,
                    incomeACKeyString, expenseACKeyString, costACKeyString,
                    doShowAll);
        } else {
            throw new AssertionError(reportSubMenuState);
        }
    }
    
    private boolean isBeginDateAndEndDateValid() {
        int beginDay;
        int beginMonth;
        int beginYear;
        int endDay;
        int endMonth;
        int endYear;

        errBeginDateLb.setText("");
        errEndDateLb.setText("");

        try {
            beginDay = beginDayIB.getCustomValue();
            beginMonth = beginMonthIB.getCustomValue();
            beginYear = beginYearIB.getCustomValue();
        } catch (NumberFormatException e) {
            errBeginDateLb.setText(constants.invalidNumberMsg());
            return false;
        } catch (InvalidValueException e) {
            errBeginDateLb.setText(constants.invalidNumberMsg());
            return false;
        }
        
        try {
            endDay = endDayIB.getCustomValue();
            endMonth = endMonthIB.getCustomValue();
            endYear = endYearIB.getCustomValue();
        } catch (NumberFormatException e) {
            errEndDateLb.setText(constants.invalidNumberMsg());
            return false;
        } catch (InvalidValueException e) {
            errEndDateLb.setText(constants.invalidNumberMsg());
            return false;
        }

        if (!(beginDay == 0 && beginMonth == 0 && beginYear == 0 && endDay == 0 && endMonth == 0 && endYear == 0)
                && !(beginDay != 0 && beginMonth != 0 && beginYear != 0 && endDay != 0 && endMonth != 0 && endYear != 0)) {
            // some empty some not not allowed
            errBeginDateLb.setText(constants.invalidMsg());
            errEndDateLb.setText(constants.invalidMsg());
            return false;
        }
        
        return true;
    }
    
    private String getACKeyString(CustomSuggestBox sb, Label errLb) {
        errLb.setText("");
        try {
            String keyString = sb.getKey();
            if (keyString != null) return keyString;
        } catch (InvalidValueException e) {
        }
        errLb.setText(constants.invalidMsg());
        return null;
    }
}
