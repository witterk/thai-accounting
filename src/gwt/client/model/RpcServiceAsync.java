package gwt.client.model;

import gwt.shared.model.SAccAmt;
import gwt.shared.model.SAccChart;
import gwt.shared.model.SAccGrp;
import gwt.shared.model.SCom;
import gwt.shared.model.SComList;
import gwt.shared.model.SDocType;
import gwt.shared.model.SFiscalYear;
import gwt.shared.model.SJournalHeader;
import gwt.shared.model.SJournalType;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>RpcService</code>.
 */
public interface RpcServiceAsync {

	void getComList(String sSID, String sID, AsyncCallback<SComList> callback);
	
	void addCom(String sSID, String sID, SCom sCom, AsyncCallback<String> callback);
	void editCom(String sSID, String sID, SCom sCom, AsyncCallback<String> callback);
	void deleteCom(String sSID, String sID, String keyString, AsyncCallback<String> callback);
	
    void addFis(String sSID, String sID, String comKeyString, int setupType, SFiscalYear sFis, AsyncCallback<String> callback);
    void editFis(String sSID, String sID, SFiscalYear sFis, AsyncCallback<String> callback);
    void deleteFis(String sSID, String sID, String keyString, AsyncCallback<String> callback);
    
    void getSetup(String sSID, String sID, String fisKeyString, AsyncCallback<SFiscalYear> callback);
    
    void addJournalType(String sSID, String sID, String fisKeyString, SJournalType sJournalType, AsyncCallback<String> callback);
    void editJournalType(String sSID, String sID, SJournalType sJournalType, AsyncCallback<String> callback);
    void deleteJournalType(String sSID, String sID, String keyString, AsyncCallback<String> callback);
    
    void addDocType(String sSID, String sID, String fisKeyString, SDocType sDocType, AsyncCallback<String> callback);
    void editDocType(String sSID, String sID, SDocType sDocType, AsyncCallback<String> callback);
    void deleteDocType(String sSID, String sID, String keyString, AsyncCallback<String> callback);
    
    void addAccGrp(String sSID, String sID, String fisKeyString, SAccGrp sAccGrp, AsyncCallback<String> callback);
    void editAccGrp(String sSID, String sID, SAccGrp sAccGrp, AsyncCallback<String> callback);
    void deleteAccGrp(String sSID, String sID, String keyString, AsyncCallback<String> callback);
    
    void addAccChart(String sSID, String sID, String fisKeyString, SAccChart sAccChart, AsyncCallback<String> callback);
    void editAccChart(String sSID, String sID, String fisKeyString, SAccChart sAccChart, AsyncCallback<String> callback);
    void deleteAccChart(String sSID, String sID, String keyString, AsyncCallback<String> callback);
    
    void setBeginning(String sSID, String sID, String accChartKeyString, double beginning, AsyncCallback<String> callback);

    void getJournalListWithJT(String sSID, String sID, String fisKeyString,
            String journalTypeKeyString, int month, int year, AsyncCallback<ArrayList<SJournalHeader>> callback);

	void addJournal(String sSID, String sID, String fisKeyString, SJournalHeader sJournal, AsyncCallback<String> callback);
    void editJournal(String sSID, String sID, String fisKeyString, SJournalHeader sJournal, AsyncCallback<String> callback);
    void deleteJournal(String sSID, String sID, String fisKeyString, String keyString, AsyncCallback<String> callback);

    void getAccAmtMap(String sSID, String sID, String fisKeyString, AsyncCallback<HashMap<String, SAccAmt>> callback);
    void recalculateAccAmt(String sSID, String sID, String fisKeyString, AsyncCallback<String> callback);
}
