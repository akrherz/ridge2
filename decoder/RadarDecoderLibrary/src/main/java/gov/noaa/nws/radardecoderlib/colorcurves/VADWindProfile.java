/*
 * FourBitReflectivity.java
 *
 * Created on October 9, 2005, 5:27 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurves;
import java.awt.Color;
/**
 * Color Curve for Eight Bit Reflectivity
 * @author jburks
 */
public class VADWindProfile extends ColorCurve {
    Color[] colors = new Color[256];
     /**
     * Creates a new instance of Eight Bit Reflectivity Color Curve
      */
    public VADWindProfile() {
        colors[1] = new Color(0,0,0);
        colors[2] = new Color(142,0,158);
        colors[3] = new Color(128,128,128);
        colors[4] = new Color(127,129,127);
        colors[5] = new Color(125,131,125);
        colors[6] = new Color(123,133,123);
        colors[7] = new Color(121,135,121);
        colors[8] = new Color(119,137,119);
        colors[9] = new Color(117,139,117);
        colors[10] = new Color(115,141,115);
        colors[11] = new Color(113,143,113);
        colors[12] = new Color(111,145,111);
        colors[13] = new Color(109,147,109);
        colors[14] = new Color(107,149,107);
        colors[15] = new Color(105,151,105);
        colors[16] = new Color(104,153,104);
        colors[17] = new Color(102,155,102);
        colors[18] = new Color(100,157,100);
        colors[19] = new Color(98,159,98);
        colors[20] = new Color(96,161,96);
        colors[21] = new Color(94,163,94);
        colors[22] = new Color(92,165,92);
        colors[23] = new Color(90,167,90);
        colors[24] = new Color(88,169,88);
        colors[25] = new Color(86,170,86);
        colors[26] = new Color(84,172,84);
        colors[27] = new Color(82,174,82);
        colors[28] = new Color(80,176,80);
        colors[29] = new Color(79,178,79);
        colors[30] = new Color(77,180,77);
        colors[31] = new Color(75,182,75);
        colors[32] = new Color(73,184,73);
        colors[33] = new Color(71,186,71);
        colors[34] = new Color(69,188,69);
        colors[35] = new Color(67,190,67);
        colors[36] = new Color(65,192,65);
        colors[37] = new Color(63,194,63);
        colors[38] = new Color(61,196,61);
        colors[39] = new Color(59,198,59);
        colors[40] = new Color(57,200,57);
        colors[41] = new Color(55,202,55);
        colors[42] = new Color(54,204,54);
        colors[43] = new Color(52,206,52);
        colors[44] = new Color(50,208,50);
        colors[45] = new Color(48,210,48);
        colors[46] = new Color(46,212,46);
        colors[47] = new Color(44,213,44);
        colors[48] = new Color(42,215,42);
        colors[49] = new Color(40,217,40);
        colors[50] = new Color(38,219,38);
        colors[51] = new Color(36,221,36);
        colors[52] = new Color(34,223,34);
        colors[53] = new Color(32,225,32);
        colors[54] = new Color(30,227,30);
        colors[55] = new Color(29,229,29);
        colors[56] = new Color(27,231,27);
        colors[57] = new Color(25,233,25);
        colors[58] = new Color(23,235,23);
        colors[59] = new Color(21,237,21);
        colors[60] = new Color(19,239,19);
        colors[61] = new Color(17,241,17);
        colors[62] = new Color(15,243,15);
        colors[63] = new Color(13,245,13);
        colors[64] = new Color(11,247,11);
        colors[65] = new Color(9,249,9);
        colors[66] = new Color(7,251,7);
        colors[67] = new Color(5,253,5);
        colors[68] = new Color(19,250,15);
        colors[69] = new Color(11,232,7);
        colors[70] = new Color(11,232,7);
        colors[71] = new Color(11,232,7);
        colors[72] = new Color(11,232,7);
        colors[73] = new Color(11,232,7);
        colors[74] = new Color(11,232,7);
        colors[75] = new Color(11,232,7);
        colors[76] = new Color(11,232,7);
        colors[77] = new Color(11,232,7);
        colors[78] = new Color(11,232,7);
        colors[79] = new Color(11,232,7);
        colors[80] = new Color(11,232,7);
        colors[81] = new Color(11,232,7);
        colors[82] = new Color(11,232,7);
        colors[83] = new Color(11,232,7);
        colors[84] = new Color(17,232,9);
        colors[85] = new Color(9,199,6);
        colors[86] = new Color(9,199,6);
        colors[87] = new Color(9,199,6);
        colors[88] = new Color(9,199,6);
        colors[89] = new Color(9,199,6);
        colors[90] = new Color(9,199,6);
        colors[91] = new Color(9,199,6);
        colors[92] = new Color(9,199,6);
        colors[93] = new Color(9,199,6);
        colors[94] = new Color(9,199,6);
        colors[95] = new Color(9,199,6);
        colors[96] = new Color(9,199,6);
        colors[97] = new Color(9,199,6);
        colors[98] = new Color(9,199,6);
        colors[99] = new Color(9,199,6);
        colors[100] = new Color(8,176,5);
        colors[101] = new Color(8,176,5);
        colors[102] = new Color(8,176,5);
        colors[103] = new Color(8,176,5);
        colors[104] = new Color(8,176,5);
        colors[105] = new Color(8,176,5);
        colors[106] = new Color(8,176,5);
        colors[107] = new Color(8,176,5);
        colors[108] = new Color(8,176,5);
        colors[109] = new Color(8,176,5);
        colors[110] = new Color(7,143,4);
        colors[111] = new Color(7,143,4);
        colors[112] = new Color(7,143,4);
        colors[113] = new Color(7,143,4);
        colors[114] = new Color(7,143,4);
        colors[115] = new Color(7,143,4);
        colors[116] = new Color(7,143,4);
        colors[117] = new Color(7,143,4);
        colors[118] = new Color(7,143,4);
        colors[119] = new Color(7,143,4);
        colors[120] = new Color(5,112,3);
        colors[121] = new Color(5,112,3);
        colors[122] = new Color(5,112,3);
        colors[123] = new Color(5,112,3);
        colors[124] = new Color(5,112,3);
        colors[125] = new Color(123,148,123);
        colors[126] = new Color(120,149,120);
        colors[127] = new Color(120,149,120);
        colors[128] = new Color(120,149,120);
        colors[129] = new Color(123,148,123);
        colors[130] = new Color(128,128,128);
        colors[131] = new Color(149,117,117);
        colors[132] = new Color(149,117,117);
        colors[133] = new Color(149,117,117);
        colors[134] = new Color(149,117,117);
        colors[135] = new Color(149,117,117);
        colors[136] = new Color(135,0,0);
        colors[137] = new Color(135,0,0);
        colors[138] = new Color(135,0,0);
        colors[139] = new Color(135,0,0);
        colors[140] = new Color(135,0,0);
        colors[141] = new Color(161,0,0);
        colors[142] = new Color(161,0,0);
        colors[143] = new Color(161,0,0);
        colors[144] = new Color(161,0,0);
        colors[145] = new Color(161,0,0);
        colors[146] = new Color(161,0,0);
        colors[147] = new Color(161,0,0);
        colors[148] = new Color(161,0,0);
        colors[149] = new Color(161,0,0);
        colors[150] = new Color(161,0,0);
        colors[151] = new Color(184,0,0);
        colors[152] = new Color(184,0,0);
        colors[153] = new Color(184,0,0);
        colors[154] = new Color(184,0,0);
        colors[155] = new Color(184,0,0);
        colors[156] = new Color(184,0,0);
        colors[157] = new Color(184,0,0);
        colors[158] = new Color(184,0,0);
        colors[159] = new Color(184,0,0);
        colors[160] = new Color(184,0,0);
        colors[161] = new Color(217,0,0);
        colors[162] = new Color(217,0,0);
        colors[163] = new Color(217,0,0);
        colors[164] = new Color(217,0,0);
        colors[165] = new Color(217,0,0);
        colors[166] = new Color(217,0,0);
        colors[167] = new Color(217,0,0);
        colors[168] = new Color(217,0,0);
        colors[169] = new Color(217,0,0);
        colors[170] = new Color(217,0,0);
        colors[171] = new Color(217,0,0);
        colors[172] = new Color(217,0,0);
        colors[173] = new Color(217,0,0);
        colors[174] = new Color(217,0,0);
        colors[175] = new Color(217,0,0);
        colors[176] = new Color(240,0,0);
        colors[177] = new Color(240,0,0);
        colors[178] = new Color(240,0,0);
        colors[179] = new Color(240,0,0);
        colors[180] = new Color(240,0,0);
        colors[181] = new Color(240,0,0);
        colors[182] = new Color(240,0,0);
        colors[183] = new Color(240,0,0);
        colors[184] = new Color(240,0,0);
        colors[185] = new Color(240,0,0);
        colors[186] = new Color(240,0,0);
        colors[187] = new Color(240,0,0);
        colors[188] = new Color(240,0,0);
        colors[189] = new Color(240,0,0);
        colors[190] = new Color(240,0,0);
        colors[191] = new Color(240,0,0);
        colors[192] = new Color(255,18,18);
        colors[193] = new Color(255,0,0);
        colors[194] = new Color(255,4,4);
        colors[195] = new Color(255,8,8);
        colors[196] = new Color(255,12,12);
        colors[197] = new Color(255,16,16);
        colors[198] = new Color(255,20,20);
        colors[199] = new Color(255,24,24);
        colors[200] = new Color(255,28,28);
        colors[201] = new Color(255,32,32);
        colors[202] = new Color(255,36,36);
        colors[203] = new Color(255,40,40);
        colors[204] = new Color(255,44,44);
        colors[205] = new Color(255,48,48);
        colors[206] = new Color(255,52,52);
        colors[207] = new Color(255,56,56);
        colors[208] = new Color(255,60,60);
        colors[209] = new Color(255,64,64);
        colors[210] = new Color(255,68,68);
        colors[211] = new Color(255,72,72);
        colors[212] = new Color(255,76,76);
        colors[213] = new Color(255,80,80);
        colors[214] = new Color(255,84,84);
        colors[215] = new Color(255,89,89);
        colors[216] = new Color(255,93,93);
        colors[217] = new Color(255,97,97);
        colors[218] = new Color(255,101,101);
        colors[219] = new Color(255,105,105);
        colors[220] = new Color(255,109,109);
        colors[221] = new Color(255,113,113);
        colors[222] = new Color(255,117,117);
        colors[223] = new Color(255,121,121);
        colors[224] = new Color(255,125,125);
        colors[225] = new Color(255,129,129);
        colors[226] = new Color(255,133,133);
        colors[227] = new Color(255,137,137);
        colors[228] = new Color(255,141,141);
        colors[229] = new Color(255,145,145);
        colors[230] = new Color(255,149,149);
        colors[231] = new Color(255,153,153);
        colors[232] = new Color(255,157,157);
        colors[233] = new Color(255,161,161);
        colors[234] = new Color(255,165,165);
        colors[235] = new Color(255,169,169);
        colors[236] = new Color(255,174,174);
        colors[237] = new Color(255,178,178);
        colors[238] = new Color(255,182,182);
        colors[239] = new Color(255,186,186);
        colors[240] = new Color(255,190,190);
        colors[241] = new Color(255,194,194);
        colors[242] = new Color(255,198,198);
        colors[243] = new Color(255,202,202);
        colors[244] = new Color(255,206,206);
        colors[245] = new Color(255,210,210);
        colors[246] = new Color(255,214,214);
        colors[247] = new Color(255,218,218);
        colors[248] = new Color(255,222,222);
        colors[249] = new Color(255,226,226);
        colors[250] = new Color(255,230,230);
        colors[251] = new Color(255,234,234);
        colors[252] = new Color(255,238,238);
        colors[253] = new Color(255,242,242);
        colors[254] = new Color(255,246,246);
        colors[255] = new Color(255,250,250);
        
        
    }

    /**
     * Returns array of Colors
     * @return get array of colors from this color curve
     */
    public Color[] getColors() {
        return(colors);
    }
    
}
