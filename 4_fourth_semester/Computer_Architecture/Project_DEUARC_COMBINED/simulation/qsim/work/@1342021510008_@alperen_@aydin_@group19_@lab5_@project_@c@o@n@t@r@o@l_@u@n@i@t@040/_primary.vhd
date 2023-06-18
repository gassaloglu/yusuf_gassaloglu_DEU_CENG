library verilog;
use verilog.vl_types.all;
entity \2021510008_Alperen_Aydin_Group19_Lab5_Project_CONTROL_UNIT\ is
    port(
        O_IR_LOAD       : out    vl_logic;
        MASTER_CLOCK    : in     vl_logic;
        MEMORY_CLOCK    : in     vl_logic;
        INP_LOAD        : in     vl_logic;
        INP_DATA        : in     vl_logic_vector(3 downto 0);
        O_PC_COUNT      : out    vl_logic;
        O_PC_LOAD       : out    vl_logic;
        O_AR_LOAD       : out    vl_logic;
        O_SP_COUNT      : out    vl_logic;
        O_DATA_MEM_WREN : out    vl_logic;
        O_STACK_MEM_WREN: out    vl_logic;
        O_R0_LOAD       : out    vl_logic;
        O_R1_LOAD       : out    vl_logic;
        O_R2_LOAD       : out    vl_logic;
        O_Q             : out    vl_logic;
        O_SC_CLEAR      : out    vl_logic;
        O_S             : out    vl_logic;
        O_F             : out    vl_logic;
        O_E             : out    vl_logic;
        O_OUT_LOAD      : out    vl_logic;
        O_AR            : out    vl_logic_vector(3 downto 0);
        O_INP           : out    vl_logic_vector(3 downto 0);
        O_IR            : out    vl_logic_vector(10 downto 0);
        O_OUT           : out    vl_logic_vector(3 downto 0);
        O_PC            : out    vl_logic_vector(4 downto 0);
        O_R0            : out    vl_logic_vector(3 downto 0);
        O_R1            : out    vl_logic_vector(3 downto 0);
        O_R2            : out    vl_logic_vector(3 downto 0);
        O_T             : out    vl_logic_vector(2 downto 0)
    );
end \2021510008_Alperen_Aydin_Group19_Lab5_Project_CONTROL_UNIT\;
