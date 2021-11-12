package com.kriptops.wizarpos.cardlib;

import com.kriptops.wizarpos.cardlib.kernel.AID;
import com.kriptops.wizarpos.cardlib.tools.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Defaults {

    public static final List<AID> AID_TABLES;

    static {
        List<AID> tables = new LinkedList<>();
        AID aid;
        // AIDS de visa
        aid = new AID();
        aid.setAid("A0000000031010" );
        aid.setAppLabel("AID2" );
        aid.setAppPreferredName("AEEFFF" );
        aid.setAppPriority((byte) 0x00);
        aid.setTermFloorLimit(0);
        aid.setTACDefault("0000000000" );
        aid.setTACDenial("0000000000" );
        aid.setTACOnline("0000000000" );
        aid.setTargetPercentage((byte) 0x00);
        aid.setThresholdValue((byte) 0x00);
        aid.setMaxTargetPercentage((byte) 0x00);
        aid.setAcquirerId("000000123456" );
        aid.setMCC("3333" );
        aid.setMID("12345678" );
        aid.setAppVersionNumber("0096" );
        aid.setPOSEntryMode((byte) 0x80);
        aid.setTransReferCurrencyCode("0604" );
        aid.setTransReferCurrencyExponent((byte) 0x02);
        aid.setDefaultDDOL("9F37049F47018F019F3201" );
        aid.setDefaultTDOL("9F0804" );
        aid.setSupportOnlinePin((byte) 0x01);
        aid.setNeedCompleteMatching((byte) 0x00);
        aid.setTermRiskManageData("" );
        aid.setContactlessLimit(1000000l); // 10000 como transaction limit contactless
        aid.setCvmLimit(15000); // 150 unidades como CVM limit
        aid.setContactlessFloorLimit(0l); // online only zero floor limit
        aid.setKernelConfig((byte) 0x20);
        aid.setCtlOnDeviceCVM(1000000l); // cl limit when on device CMV is active, must match the set contactless limit
        aid.setCtlNoOnDeviceCVM(1000000l); // cl limit when no on device CMV is active, must match the set contactless limit
        aid.setCvmCapCVMRequired((byte) 0x00);
        aid.setCvmCapNoCVMRequired((byte) 0x00);
        aid.setMscvmCapCVMRequired((byte) 0x00);
        aid.setMscvmCapNoCVMRequired((byte) 0x00);
        aid.setContactlessKernelID((byte) 0xFF);
        aid.setAuc("FFC0");
        tables.add(aid);

        aid = new AID();
        aid.setAid("A0000000032010" );
        aid.setAppLabel("AID2" );
        aid.setAppPreferredName("AEEFFF" );
        aid.setAppPriority((byte) 0x00);
        aid.setTermFloorLimit(0);
        aid.setTACDefault("0000000000" );
        aid.setTACDenial("0000000000" );
        aid.setTACOnline("0000000000" );
        aid.setTargetPercentage((byte) 0x00);
        aid.setThresholdValue((byte) 0x00);
        aid.setMaxTargetPercentage((byte) 0x00);
        aid.setAcquirerId("000000123456" );
        aid.setMCC("3333" );
        aid.setMID("12345678" );
        aid.setAppVersionNumber("0096" );
        aid.setPOSEntryMode((byte) 0x80);
        aid.setTransReferCurrencyCode("0604" );
        aid.setTransReferCurrencyExponent((byte) 0x02);
        aid.setDefaultDDOL("9F37049F47018F019F3201" );
        aid.setDefaultTDOL("9F0804" );
        aid.setSupportOnlinePin((byte) 0x01);
        aid.setNeedCompleteMatching((byte) 0x00);
        aid.setTermRiskManageData("" );
        aid.setContactlessLimit(1000000l); // 10000 como transaction limit contactless
        aid.setCvmLimit(15000); // 150 unidades como CVM limit
        aid.setContactlessFloorLimit(0l); // online only zero floor limit
        aid.setKernelConfig((byte) 0x20);
        aid.setContactlessKernelID((byte) 0xFF);
        //solo para aids de mastercard
        aid.setCtlOnDeviceCVM(0l);
        aid.setCtlNoOnDeviceCVM(0l);
        aid.setCvmCapCVMRequired((byte) 0x00);
        aid.setCvmCapNoCVMRequired((byte) 0x00);
        aid.setMscvmCapCVMRequired((byte) 0x00);
        aid.setMscvmCapNoCVMRequired((byte) 0x00);
        aid.setAuc("FFC0");
        tables.add(aid);

        aid = new AID();
        aid.setAid("A0000000033010" );
        aid.setAppLabel("AID2" );
        aid.setAppPreferredName("AEEFFF" );
        aid.setAppPriority((byte) 0x00);
        aid.setTermFloorLimit(0);
        aid.setTACDefault("0000000000" );
        aid.setTACDenial("0000000000" );
        aid.setTACOnline("0000000000" );
        aid.setTargetPercentage((byte) 0x00);
        aid.setThresholdValue((byte) 0x00);
        aid.setMaxTargetPercentage((byte) 0x00);
        aid.setAcquirerId("000000123456" );
        aid.setMCC("3333" );
        aid.setMID("12345678" );
        aid.setAppVersionNumber("0096" );
        aid.setPOSEntryMode((byte) 0x80);
        aid.setTransReferCurrencyCode("0604" );
        aid.setTransReferCurrencyExponent((byte) 0x02);
        aid.setDefaultDDOL("9F37049F47018F019F3201" );
        aid.setDefaultTDOL("9F0804" );
        aid.setSupportOnlinePin((byte) 0x01);
        aid.setNeedCompleteMatching((byte) 0x00);
        aid.setTermRiskManageData("" );
        aid.setContactlessLimit(1000000l); // 10000 como transaction limit contactless
        aid.setCvmLimit(15000); // 150 unidades como CVM limit
        aid.setContactlessFloorLimit(0l); // online only zero floor limit
        aid.setKernelConfig((byte) 0x20);
        aid.setContactlessKernelID((byte) 0xFF);
        //solo para aids de mastercard
        aid.setCtlOnDeviceCVM(0l);
        aid.setCtlNoOnDeviceCVM(0l);
        aid.setCvmCapCVMRequired((byte) 0x00);
        aid.setCvmCapNoCVMRequired((byte) 0x00);
        aid.setMscvmCapCVMRequired((byte) 0x00);
        aid.setMscvmCapNoCVMRequired((byte) 0x00);
        aid.setAuc("FFC0");
        tables.add(aid);

        // AIDS de Mastercard
        aid = new AID();
        aid.setAid("A0000000041010" );
        aid.setAppLabel("AID2" );
        aid.setAppPreferredName("AEEFFF" );
        aid.setAppPriority((byte) 0);
        aid.setTermFloorLimit(10000);
        aid.setTACDefault("0000000000" );
        aid.setTACDenial("0000000000" );
        aid.setTACOnline("0000000000" );
        aid.setTargetPercentage((byte) 0);
        aid.setThresholdValue(0);
        aid.setMaxTargetPercentage((byte) 0x00);
        aid.setAcquirerId("000000123456" );
        aid.setMCC("3333" );
        aid.setMID("12345678" );
        aid.setAppVersionNumber("0002" );
        aid.setPOSEntryMode((byte) 0xFF);
        aid.setTransReferCurrencyCode("0840" );
        aid.setTransReferCurrencyExponent((byte) 0x02);
        aid.setDefaultDDOL("9F37049F47018F019F3201" );
        aid.setDefaultTDOL("9F0804" );
        aid.setSupportOnlinePin((byte) 0x01);
        aid.setNeedCompleteMatching((byte) 0x00);
        aid.setTermRiskManageData("" );
        aid.setContactlessLimit(1000000l); // 10000 como transaction limit contactless
        aid.setCvmLimit(15000); // 150 unidades como CVM limit
        aid.setContactlessFloorLimit(0l); // online only zero floor limit
        aid.setKernelConfig((byte) 0x20);
        aid.setContactlessKernelID((byte) 0xFF);
        //solo para aids de mastercard
        aid.setCtlOnDeviceCVM(aid.getContactlessLimit());
        aid.setCtlNoOnDeviceCVM(aid.getContactlessLimit());
        aid.setCvmCapCVMRequired((byte) 0x60);
        aid.setCvmCapNoCVMRequired((byte) 0x68);
        aid.setMscvmCapCVMRequired((byte) 0x20);
        aid.setMscvmCapNoCVMRequired((byte) 0x20);
        tables.add(aid);

        aid = new AID();
        aid.setAid("A0000000042203" );
        aid.setAppLabel("AID2" );
        aid.setAppPreferredName("AEEFFF" );
        aid.setAppPriority((byte) 0);
        aid.setTermFloorLimit(10000);
        aid.setTACDefault("0000000000" );
        aid.setTACDenial("0000000000" );
        aid.setTACOnline("0000000000" );
        aid.setTargetPercentage((byte) 0);
        aid.setThresholdValue(0);
        aid.setMaxTargetPercentage((byte) 0x00);
        aid.setAcquirerId("000000123456" );
        aid.setMCC("3333" );
        aid.setMID("12345678" );
        aid.setAppVersionNumber("0002" );
        aid.setPOSEntryMode((byte) 0xFF);
        aid.setTransReferCurrencyCode("0840" );
        aid.setTransReferCurrencyExponent((byte) 0x02);
        aid.setDefaultDDOL("9F37049F47018F019F3201" );
        aid.setDefaultTDOL("9F0804" );
        aid.setSupportOnlinePin((byte) 0x01);
        aid.setNeedCompleteMatching((byte) 0x00);
        aid.setTermRiskManageData("" );
        aid.setContactlessLimit(1000000l); // 10000 como transaction limit contactless
        aid.setCvmLimit(15000); // 150 unidades como CVM limit
        aid.setContactlessFloorLimit(0l); // online only zero floor limit
        aid.setKernelConfig((byte) 0x20);
        aid.setContactlessKernelID((byte) 0xFF);
        //solo para aids de mastercard
        aid.setCtlOnDeviceCVM(aid.getContactlessLimit());
        aid.setCtlNoOnDeviceCVM(aid.getContactlessLimit());
        aid.setCvmCapCVMRequired((byte) 0x60);
        aid.setCvmCapNoCVMRequired((byte) 0x68);
        aid.setMscvmCapCVMRequired((byte) 0x20);
        aid.setMscvmCapNoCVMRequired((byte) 0x20);
        tables.add(aid);

        aid = new AID();
        aid.setAid("A0000000043060" );
        aid.setAppLabel("AID2" );
        aid.setAppPreferredName("AEEFFF" );
        aid.setAppPriority((byte) 0);
        aid.setTermFloorLimit(10000);
        aid.setTACDefault("0000000000" );
        aid.setTACDenial("0000000000" );
        aid.setTACOnline("0000000000" );
        aid.setTargetPercentage((byte) 0);
        aid.setThresholdValue(0);
        aid.setMaxTargetPercentage((byte) 0x00);
        aid.setAcquirerId("000000123456" );
        aid.setMCC("3333" );
        aid.setMID("12345678" );
        aid.setAppVersionNumber("0002" );
        aid.setPOSEntryMode((byte) 0xFF);
        aid.setTransReferCurrencyCode("0840" );
        aid.setTransReferCurrencyExponent((byte) 0x02);
        aid.setDefaultDDOL("9F37049F47018F019F3201" );
        aid.setDefaultTDOL("9F0804" );
        aid.setSupportOnlinePin((byte) 0x01);
        aid.setNeedCompleteMatching((byte) 0x00);
        aid.setTermRiskManageData("" );
        aid.setContactlessLimit(1000000l); // 10000 como transaction limit contactless
        aid.setCvmLimit(15000); // 150 unidades como CVM limit
        aid.setContactlessFloorLimit(0l); // online only zero floor limit
        aid.setKernelConfig((byte) 0x20);
        aid.setContactlessKernelID((byte) 0xFF);
        //solo para aids de mastercard
        aid.setCtlOnDeviceCVM(aid.getContactlessLimit());
        aid.setCtlNoOnDeviceCVM(aid.getContactlessLimit());
        aid.setCvmCapCVMRequired((byte) 0x60);
        aid.setCvmCapNoCVMRequired((byte) 0x68);
        aid.setMscvmCapCVMRequired((byte) 0x20);
        aid.setMscvmCapNoCVMRequired((byte) 0x20);
        tables.add(aid);

        AID_TABLES = Collections.unmodifiableList(tables);
    }

    public static final List<String> AIDS = Collections.unmodifiableList(Arrays.asList(
            "9F0607A0000000031010DF0101009F08020096DF11050000000000DF12050000000000DF130500000000009F1B0400000000DF150400000000DF160100DF170100DF140B9F37049F47018F019F3201DF1801015004414944329F12064145454646468701009F160F3132333435363738202020202020209F01060000001234569F150233339F3901809F3C0208409F3D0102DF22039F0804DF1906000000000000DF2006009999999900DF2106000000015000",
            "9F0607A0000000041010DF0101009F08020002DF11050000000000DF12050000000000DF130500000000009F1B0400000000DF150400000000DF160100DF170100DF140B9F37049F47018F019F3201DF1801015004414944329F12064145454646468701009F160F3132333435363738202020202020209F01060000001234569F150233339F3901809F3C0208409F3D0102DF22039F0804DF1906000000000000DF2006009999999900DF2106000000015000DF812406009999999900DF812506009999999900DF81180160DF81190108DF811E0120DF812C0120DF811B0120"

    ));

    public static final List<String> CAPKS = Collections.unmodifiableList(Arrays.asList(
            "9F0605A0000000039F220108DF05083230323431323331DF060101DF070101DF0281B0D9FD6ED75D51D0E30664BD157023EAA1FFA871E4DA65672B863D255E81E137A51DE4F72BCC9E44ACE12127F87E263D3AF9DD9CF35CA4A7B01E907000BA85D24954C2FCA3074825DDD4C0C8F186CB020F683E02F2DEAD3969133F06F7845166ACEB57CA0FC2603445469811D293BFEFBAFAB57631B3DD91E796BF850A25012F1AE38F05AA5C4D6D03B1DC2E568612785938BBC9B3CD3A910C1DA55A5A9218ACE0F7A21287752682F15832A678D6E1ED0BDF040103DF031420D213126955DE205ADC2FD2822BD22DE21CF9A8",
            "9F0605A0000000039F220109DF05083230323731323331DF060101DF070101DF0281F89D912248DE0A4E39C1A7DDE3F6D2588992C1A4095AFBD1824D1BA74847F2BC4926D2EFD904B4B54954CD189A54C5D1179654F8F9B0D2AB5F0357EB642FEDA95D3912C6576945FAB897E7062CAA44A4AA06B8FE6E3DBA18AF6AE3738E30429EE9BE03427C9D64F695FA8CAB4BFE376853EA34AD1D76BFCAD15908C077FFE6DC5521ECEF5D278A96E26F57359FFAEDA19434B937F1AD999DC5C41EB11935B44C18100E857F431A4A5A6BB65114F174C2D7B59FDF237D6BB1DD0916E644D709DED56481477C75D95CDD68254615F7740EC07F330AC5D67BCD75BF23D28A140826C026DBDE971A37CD3EF9B8DF644AC385010501EFC6509D7A41DF040103DF03141FF80A40173F52D7D27E0F26A146A1C8CCB29046",
            "9F0605A0000000049F220104DF05083230313731323331DF060101DF070101DF028190A6DA428387A502D7DDFB7A74D3F412BE762627197B25435B7A81716A700157DDD06F7CC99D6CA28C2470527E2C03616B9C59217357C2674F583B3BA5C7DCF2838692D023E3562420B4615C439CA97C44DC9A249CFCE7B3BFB22F68228C3AF13329AA4A613CF8DD853502373D62E49AB256D2BC17120E54AEDCED6D96A4287ACC5C04677D4A5A320DB8BEE2F775E5FEC5DF040103DF0314381A035DA58B482EE2AF75F4C3F2CA469BA4AA6C",
            "9F0605A0000000049F220105DF05083230323331323331DF060101DF070101DF0281B0B8048ABC30C90D976336543E3FD7091C8FE4800DF820ED55E7E94813ED00555B573FECA3D84AF6131A651D66CFF4284FB13B635EDD0EE40176D8BF04B7FD1C7BACF9AC7327DFAA8AA72D10DB3B8E70B2DDD811CB4196525EA386ACC33C0D9D4575916469C4E4F53E8E1C912CC618CB22DDE7C3568E90022E6BBA770202E4522A2DD623D180E215BD1D1507FE3DC90CA310D27B3EFCCD8F83DE3052CAD1E48938C68D095AAC91B5F37E28BB49EC7ED597DF040103DF0314EBFA0D5D06D8CE702DA3EAE890701D45E274C845",
            "9F0605A0000000049F220106DF05083230323331323331DF060101DF070101DF0281F8CB26FC830B43785B2BCE37C81ED334622F9622F4C89AAE641046B2353433883F307FB7C974162DA72F7A4EC75D9D657336865B8D3023D3D645667625C9A07A6B7A137CF0C64198AE38FC238006FB2603F41F4F3BB9DA1347270F2F5D8C606E420958C5F7D50A71DE30142F70DE468889B5E3A08695B938A50FC980393A9CBCE44AD2D64F630BB33AD3F5F5FD495D31F37818C1D94071342E07F1BEC2194F6035BA5DED3936500EB82DFDA6E8AFB655B1EF3D0D7EBF86B66DD9F29F6B1D324FE8B26CE38AB2013DD13F611E7A594D675C4432350EA244CC34F3873CBA06592987A1D7E852ADC22EF5A2EE28132031E48F74037E3B34AB747FDF040103DF0314F910A1504D5FFB793D94F3B500765E1ABCAD72D9",
            "9F0605A0000000659F220110DF05083230313731323331DF060101DF070101DF02819099B63464EE0B4957E4FD23BF923D12B61469B8FFF8814346B2ED6A780F8988EA9CF0433BC1E655F05EFA66D0C98098F25B659D7A25B8478A36E489760D071F54CDF7416948ED733D816349DA2AADDA227EE45936203CBF628CD033AABA5E5A6E4AE37FBACB4611B4113ED427529C636F6C3304F8ABDD6D9AD660516AE87F7F2DDF1D2FA44C164727E56BBC9BA23C0285DF040103DF0314C75E5210CBE6E8F0594A0F1911B07418CADB5BAB",
            "9F0605A0000000659F220112DF05083230323431323331DF060101DF070101DF0281B0ADF05CD4C5B490B087C3467B0F3043750438848461288BFEFD6198DD576DC3AD7A7CFA07DBA128C247A8EAB30DC3A30B02FCD7F1C8167965463626FEFF8AB1AA61A4B9AEF09EE12B009842A1ABA01ADB4A2B170668781EC92B60F605FD12B2B2A6F1FE734BE510F60DC5D189E401451B62B4E06851EC20EBFF4522AACC2E9CDC89BC5D8CDE5D633CFD77220FF6BBD4A9B441473CC3C6FEFC8D13E57C3DE97E1269FA19F655215B23563ED1D1860D8681DF040103DF0314874B379B7F607DC1CAF87A19E400B6A9E25163E8",
            "9F0605A0000000659F220114DF05083230323531323331DF060101DF070101DF0281F8AEED55B9EE00E1ECEB045F61D2DA9A66AB637B43FB5CDBDB22A2FBB25BE061E937E38244EE5132F530144A3F268907D8FD648863F5A96FED7E42089E93457ADC0E1BC89C58A0DB72675FBC47FEE9FF33C16ADE6D341936B06B6A6F5EF6F66A4EDD981DF75DA8399C3053F430ECA342437C23AF423A211AC9F58EAF09B0F837DE9D86C7109DB1646561AA5AF0289AF5514AC64BC2D9D36A179BB8A7971E2BFA03A9E4B847FD3D63524D43A0E8003547B94A8A75E519DF3177D0A60BC0B4BAB1EA59A2CBB4D2D62354E926E9C7D3BE4181E81BA60F8285A896D17DA8C3242481B6C405769A39D547C74ED9FF95A70A796046B5EFF36682DC29DF040103DF0314C0D15F6CD957E491DB56DCDD1CA87A03EBE06B7B",
            "9F0605A0000003339F220102DF05083230323131323331DF060101DF070101DF028190A3767ABD1B6AA69D7F3FBF28C092DE9ED1E658BA5F0909AF7A1CCD907373B7210FDEB16287BA8E78E1529F443976FD27F991EC67D95E5F4E96B127CAB2396A94D6E45CDA44CA4C4867570D6B07542F8D4BF9FF97975DB9891515E66F525D2B3CBEB6D662BFB6C3F338E93B02142BFC44173A3764C56AADD202075B26DC2F9F7D7AE74BD7D00FD05EE430032663D27A57DF040103DF031403BB335A8549A03B87AB089D006F60852E4B8060",
            "9F0605A0000003339F220103DF05083230323431323331DF060101DF070101DF0281B0B0627DEE87864F9C18C13B9A1F025448BF13C58380C91F4CEBA9F9BCB214FF8414E9B59D6ABA10F941C7331768F47B2127907D857FA39AAF8CE02045DD01619D689EE731C551159BE7EB2D51A372FF56B556E5CB2FDE36E23073A44CA215D6C26CA68847B388E39520E0026E62294B557D6470440CA0AEFC9438C923AEC9B2098D6D3A1AF5E8B1DE36F4B53040109D89B77CAFAF70C26C601ABDF59EEC0FDC8A99089140CD2E817E335175B03B7AA33DDF040103DF031487F0CD7C0E86F38F89A66F8C47071A8B88586F26",
            "9F0605A0000003339F220104DF05083230323731323331DF060101DF070101DF0281F8BC853E6B5365E89E7EE9317C94B02D0ABB0DBD91C05A224A2554AA29ED9FCB9D86EB9CCBB322A57811F86188AAC7351C72BD9EF196C5A01ACEF7A4EB0D2AD63D9E6AC2E7836547CB1595C68BCBAFD0F6728760F3A7CA7B97301B7E0220184EFC4F653008D93CE098C0D93B45201096D1ADFF4CF1F9FC02AF759DA27CD6DFD6D789B099F16F378B6100334E63F3D35F3251A5EC78693731F5233519CDB380F5AB8C0F02728E91D469ABD0EAE0D93B1CC66CE127B29C7D77441A49D09FCA5D6D9762FC74C31BB506C8BAE3C79AD6C2578775B95956B5370D1D0519E37906B384736233251E8F09AD79DFBE2C6ABFADAC8E4D8624318C27DAF1DF040103DF0314F527081CF371DD7E1FD4FA414A665036E0F5E6E5",
            "9F0605A0000000039F220195DF05083230313831323331DF060101DF070101DF028190BE9E1FA5E9A803852999C4AB432DB28600DCD9DAB76DFAAA47355A0FE37B1508AC6BF38860D3C6C2E5B12A3CAAF2A7005A7241EBAA7771112C74CF9A0634652FBCA0E5980C54A64761EA101A114E0F0B5572ADD57D010B7C9C887E104CA4EE1272DA66D997B9A90B5A6D624AB6C57E73C8F919000EB5F684898EF8C3DBEFB330C62660BED88EA78E909AFF05F6DA627BDF040103DF0314EE1511CEC71020A9B90443B37B1D5F6E703030F6",
            "9F0605A0000000039F220192DF05083230313831323331DF060101DF070101DF0281B0996AF56F569187D09293C14810450ED8EE3357397B18A2458EFAA92DA3B6DF6514EC060195318FD43BE9B8F0CC669E3F844057CBDDF8BDA191BB64473BC8DC9A730DB8F6B4EDE3924186FFD9B8C7735789C23A36BA0B8AF65372EB57EA5D89E7D14E9C7B6B557460F10885DA16AC923F15AF3758F0F03EBD3C5C2C949CBA306DB44E6A2C076C5F67E281D7EF56785DC4D75945E491F01918800A9E2DC66F60080566CE0DAF8D17EAD46AD8E30A247C9FDF040103DF0314429C954A3859CEF91295F663C963E582ED6EB253",
            "9F0605A0000000039F220194DF05083230313831323331DF060101DF070101DF0281F8ACD2B12302EE644F3F835ABD1FC7A6F62CCE48FFEC622AA8EF062BEF6FB8BA8BC68BBF6AB5870EED579BC3973E121303D34841A796D6DCBC41DBF9E52C4609795C0CCF7EE86FA1D5CB041071ED2C51D2202F63F1156C58A92D38BC60BDF424E1776E2BC9648078A03B36FB554375FC53D57C73F5160EA59F3AFC5398EC7B67758D65C9BFF7828B6B82D4BE124A416AB7301914311EA462C19F771F31B3B57336000DFF732D3B83DE07052D730354D297BEC72871DCCF0E193F171ABA27EE464C6A97690943D59BDABB2A27EB71CEEBDAFA1176046478FD62FEC452D5CA393296530AA3F41927ADFE434A2DF2AE3054F8840657A26E0FC617DF040103DF0314C4A3C43CCF87327D136B804160E47D43B60E6E0F",
            "9F0605A0000000039F220157DF05083230313831323331DF060101DF070101DF028160942B7F2BA5EA307312B63DF77C5243618ACC2002BD7ECB74D821FE7BDC78BF28F49F74190AD9B23B9713B140FFEC1FB429D93F56BDC7ADE4AC075D75532C1E590B21874C7952F29B8C0F0C1CE3AEEDC8DA25343123E71DCF86C6998E15F756E3DF0403010001DF0314251A5F5DE61CF28B5C6E2B5807C0644A01D46FF5",
            "9F0605A0000000049F2201EFDF05083230313831323331DF060101DF070101DF0281F8A191CB87473F29349B5D60A88B3EAEE0973AA6F1A082F358D849FDDFF9C091F899EDA9792CAF09EF28F5D22404B88A2293EEBBC1949C43BEA4D60CFD879A1539544E09E0F09F60F065B2BF2A13ECC705F3D468B9D33AE77AD9D3F19CA40F23DCF5EB7C04DC8F69EBA565B1EBCB4686CD274785530FF6F6E9EE43AA43FDB02CE00DAEC15C7B8FD6A9B394BABA419D3F6DC85E16569BE8E76989688EFEA2DF22FF7D35C043338DEAA982A02B866DE5328519EBBCD6F03CDD686673847F84DB651AB86C28CF1462562C577B853564A290C8556D818531268D25CC98A4CC6A0BDFFFDA2DCCA3A94C998559E307FDDF915006D9A987B07DDAEB3BDF040103DF031421766EBB0EE122AFB65D7845B73DB46BAB65427A",
            "9F0605A0000000049F2201F1DF05083230313831323331DF060101DF070101DF0281B0A0DCF4BDE19C3546B4B6F0414D174DDE294AABBB828C5A834D73AAE27C99B0B053A90278007239B6459FF0BBCD7B4B9C6C50AC02CE91368DA1BD21AAEADBC65347337D89B68F5C99A09D05BE02DD1F8C5BA20E2F13FB2A27C41D3F85CAD5CF6668E75851EC66EDBF98851FD4E42C44C1D59F5984703B27D5B9F21B8FA0D93279FBBF69E090642909C9EA27F898959541AA6757F5F624104F6E1D3A9532F2A6E51515AEAD1B43B3D7835088A2FAFA7BE7DF040103DF0314D8E68DA167AB5A85D8C3D55ECB9B0517A1A5B4BB",
            "9F0605A0000000049F2201F3DF05083230313831323331DF060101DF070101DF02819098F0C770F23864C2E766DF02D1E833DFF4FFE92D696E1642F0A88C5694C6479D16DB1537BFE29E4FDC6E6E8AFD1B0EB7EA0124723C333179BF19E93F10658B2F776E829E87DAEDA9C94A8B3382199A350C077977C97AFF08FD11310AC950A72C3CA5002EF513FCCC286E646E3C5387535D509514B3B326E1234F9CB48C36DDD44B416D23654034A66F403BA511C5EFA3DF040103DF0314A69AC7603DAF566E972DEDC2CB433E07E8B01A9A",
            "9F0605A0000000049F2201F5DF05083230313831323331DF060101DF070101DF0281F8A6E6FB72179506F860CCCA8C27F99CECD94C7D4F3191D303BBEE37481C7AA15F233BA755E9E4376345A9A67E7994BDC1C680BB3522D8C93EB0CCC91AD31AD450DA30D337662D19AC03E2B4EF5F6EC18282D491E19767D7B24542DFDEFF6F62185503532069BBB369E3BB9FB19AC6F1C30B97D249EEE764E0BAC97F25C873D973953E5153A42064BBFABFD06A4BB486860BF6637406C9FC36813A4A75F75C31CCA9F69F8DE59ADECEF6BDE7E07800FCBE035D3176AF8473E23E9AA3DFEE221196D1148302677C720CFE2544A03DB553E7F1B8427BA1CC72B0F29B12DFEF4C081D076D353E71880AADFF386352AF0AB7B28ED49E1E672D11F9DF0403010001DF0314C2239804C8098170BE52D6D5D4159E81CE8466BF",
            "9F0605A0000000049F2201F6DF05083230313831323331DF060101DF070101DF0281E0A25A6BD783A5EF6B8FB6F83055C260F5F99EA16678F3B9053E0F6498E82C3F5D1E8C38F13588017E2B12B3D8FF6F50167F46442910729E9E4D1B3739E5067C0AC7A1F4487E35F675BC16E233315165CB142BFDB25E301A632A54A3371EBAB6572DEEBAF370F337F057EE73B4AE46D1A8BC4DA853EC3CC12C8CBC2DA18322D68530C70B22BDAC351DD36068AE321E11ABF264F4D3569BB71214545005558DE26083C735DB776368172FE8C2F5C85E8B5B890CC682911D2DE71FA626B8817FCCC08922B703869F3BAEAC1459D77CD85376BC36182F4238314D6C4212FBDD7F23D3DF040103DF0314502909ED545E3C8DBD00EA582D0617FEE9F6F684",
            "9F0605A0000000049F2201F7DF05083230313831323331DF060101DF070101DF02818094EA62F6D58320E354C022ADDCF0559D8CF206CD92E869564905CE21D720F971B7AEA374830EBE1757115A85E088D41C6B77CF5EC821F30B1D890417BF2FA31E5908DED5FA677F8C7B184AD09028FDDE96B6A6109850AA800175EABCDBBB684A96C2EB6379DFEA08D32FE2331FE103233AD58DCDB1E6E077CB9F24EAEC5C25AFDF0403010001DF0314EEB0DD9B2477BEE3209A914CDBA94C1C4A9BDED9",
            "9F0605A0000000049F2201F8DF05083230313831323331DF060101DF070101DF028180A1F5E1C9BD8650BD43AB6EE56B891EF7459C0A24FA84F9127D1A6C79D4930F6DB1852E2510F18B61CD354DB83A356BD190B88AB8DF04284D02A4204A7B6CB7C5551977A9B36379CA3DE1A08E69F301C95CC1C20506959275F41723DD5D2925290579E5A95B0DF6323FC8E9273D6F849198C4996209166D9BFC973C361CC826E1DF040103DF0314F06ECC6D2AAEBF259B7E755A38D9A9B24E2FF3DD",
            "9F0605A0000000049F2201F9DF05083230313831323331DF060101DF070101DF0281C0A99A6D3E071889ED9E3A0C391C69B0B804FC160B2B4BDD570C92DD5A0F45F53E8621F7C96C40224266735E1EE1B3C06238AE35046320FD8E81F8CEB3F8B4C97B940930A3AC5E790086DAD41A6A4F5117BA1CE2438A51AC053EB002AED866D2C458FD73359021A12029A0C043045C11664FE0219EC63C10BF2155BB2784609A106421D45163799738C1C30909BB6C6FE52BBB76397B9740CE064A613FF8411185F08842A423EAD20EDFFBFF1CD6C3FE0C9821479199C26D8572CC8AFFF087A9C3DF040103DF0314336712DCC28554809C6AA9B02358DE6F755164DB",
            "9F0605A0000000049F2201FADF05083230313831323331DF060101DF070101DF028190A90FCD55AA2D5D9963E35ED0F440177699832F49C6BAB15CDAE5794BE93F934D4462D5D12762E48C38BA83D8445DEAA74195A301A102B2F114EADA0D180EE5E7A5C73E0C4E11F67A43DDAB5D55683B1474CC0627F44B8D3088A492FFAADAD4F42422D0E7013536C3C49AD3D0FAE96459B0F6B1B6056538A3D6D44640F94467B108867DEC40FAAECD740C00E2B7A8852DDF040103DF03145BED4068D96EA16D2D77E03D6036FC7A160EA99C",
            "9F0605A0000003339F22010ADF05083230323731323331DF060101DF070101DF028180B2AB1B6E9AC55A75ADFD5BBC34490E53C4C3381F34E60E7FAC21CC2B26DD34462B64A6FAE2495ED1DD383B8138BEA100FF9B7A111817E7B9869A9742B19E5C9DAC56F8B8827F11B05A08ECCF9E8D5E85B0F7CFA644EFF3E9B796688F38E006DEB21E101C01028903A06023AC5AAB8635F8E307A53AC742BDCE6A283F585F48EFDF040103DF0314C88BE6B2417C4F941C9371EA35A377158767E4E3",
            "9F0605A0000003339F220108DF05083230323731323331DF060101DF070101DF028190B61645EDFD5498FB246444037A0FA18C0F101EBD8EFA54573CE6E6A7FBF63ED21D66340852B0211CF5EEF6A1CD989F66AF21A8EB19DBD8DBC3706D135363A0D683D046304F5A836BC1BC632821AFE7A2F75DA3C50AC74C545A754562204137169663CFCC0B06E67E2109EBA41BC67FF20CC8AC80D7B6EE1A95465B3B2657533EA56D92D539E5064360EA4850FED2D1BFDF040103DF0314EE23B616C95C02652AD18860E48787C079E8E85A",
            "9F0605A0000003339F220109DF05083230323731323331DF060101DF070101DF0281B0EB374DFC5A96B71D2863875EDA2EAFB96B1B439D3ECE0B1826A2672EEEFA7990286776F8BD989A15141A75C384DFC14FEF9243AAB32707659BE9E4797A247C2F0B6D99372F384AF62FE23BC54BCDC57A9ACD1D5585C303F201EF4E8B806AFB809DB1A3DB1CD112AC884F164A67B99C7D6E5A8A6DF1D3CAE6D7ED3D5BE725B2DE4ADE23FA679BF4EB15A93D8A6E29C7FFA1A70DE2E54F593D908A3BF9EBBD760BBFDC8DB8B54497E6C5BE0E4A4DAC29E5DF040103DF0314A075306EAB0045BAF72CDD33B3B678779DE1F527",
            "9F0605A0000003339F22010BDF05083230323731323331DF060101DF070101DF0281F8CF9FDF46B356378E9AF311B0F981B21A1F22F250FB11F55C958709E3C7241918293483289EAE688A094C02C344E2999F315A72841F489E24B1BA0056CFAB3B479D0E826452375DCDBB67E97EC2AA66F4601D774FEAEF775ACCC621BFEB65FB0053FC5F392AA5E1D4C41A4DE9FFDFDF1327C4BB874F1F63A599EE3902FE95E729FD78D4234DC7E6CF1ABABAA3F6DB29B7F05D1D901D2E76A606A8CBFFFFECBD918FA2D278BDB43B0434F5D45134BE1C2781D157D501FF43E5F1C470967CD57CE53B64D82974C8275937C5D8502A1252A8A5D6088A259B694F98648D9AF2CB0EFD9D943C69F896D49FA39702162ACB5AF29B90BADE005BC157DF040103DF0314BD331F9996A490B33C13441066A09AD3FEB5F66C"
    ));

    public static int TAG_LIST[] = {
            0x71,
            0x72,
            0x82,
            0x8A,
            0x91,
            0x95,
            0x9A,
            0x9C,
            0x9F02,
            0x9F03,
            0x9F10,
            0x5F2A,
            0x9F26,
            0x9F27,
            0x9F33,
            0x9F34,
            0x9F35,
            0x9F36,
            0x9F37,
            0x9F40,
            0x9F1A
    };

    public static int[] REQUIRED_TAG_LIST = {
            0x57,
            0x5A,
            0x5F34,
            0x55
    };

    public static int[] DEFAULT_NFC_TAGLIST = {
            0x5F2A,
            0x5F34,
            0x82,
            0x95,
            0x9A,
            0x9C,
            0x9F02,
            0x9F03,
            0x9F10,
            0x9F1A,
            0x9F26,
            0x9F27,
            0x9F33,
            0x9F34,
            0x9F35,
            0x9F36,
            0x9F37,
            0x9F40,
            0x9F1E,
            0x9F09,
            0x9F41,
            0x84
    };

    public static int[] DEFAULT_ICC_TAGLIST = {
            0x82,
            0x95,
            0x9A,
            0x9C,
            0x5F2A,
            0x5F34,
            0x9F02,
            0x9F03,
            0x9F10,
            0x9F1A,
            0x9F26,
            0x9F27,
            0x9F33,
            0x9F34,
            0x9F35,
            0x9F36,
            0x9F37,
            0x9F40,
            0x9F1E,
            0x9F09,
            0x9F41,
            0x5A,
            0x84
    };

    public static String LOG_TAG = "CARDLIB";

    /**
     *
     */
    public static final int MK_SLOT = 0;

    /**
     *
     */
    public static final int UK_PIN_SLOT = 0;

    /**
     *
     */
    public static final int UK_DATA_SLOT = 1;

    public static final String IV_DATA = "iv_data";
    public static final String IV_PIN = "iv_pin";
    public static final String DEFAULT_IV = "0000000000000000";

    /**
     * Timeout de lectura de pin para 60 segundos por defecto.
     */
    public static final int PINPAD_REQUEST_TIMEOUT = 60;

    public static final String[] BIN_MSR_WHITELIST = {
            "486353",
            "486352",
            "441319",
            "546342",
            "231000",
            "422394",
            "533982",
            "456871",
            "420495",
            "448165",
            "419504",
            "533635",
            "433894",
            "405893",
            "422829",
            "489486",
            "483179",
            "474631",
            "529555"
    };

}
