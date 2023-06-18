library verilog;
use verilog.vl_types.all;
entity \2020510030_Berkay-Dinc_Group19_Lab5_Project_BUS\ is
    port(
        O_AR            : out    vl_logic_vector(3 downto 0);
        CONTROL_DECODE  : in     vl_logic_vector(2 downto 0);
        MASTER_CLOCK    : in     vl_logic;
        CONTROL_INPUT   : in     vl_logic_vector(4 downto 0);
        O_INP           : out    vl_logic_vector(3 downto 0);
        INP_LOAD        : in     vl_logic;
        INP_DATA        : in     vl_logic_vector(3 downto 0);
        O_IR            : out    vl_logic_vector(10 downto 0);
        MEMORY_CLOCK    : in     vl_logic;
        O_OUT           : out    vl_logic_vector(3 downto 0);
        BUS_DECODE      : in     vl_logic_vector(2 downto 0);
        ALU_USER_INPUT_SEL: in     vl_logic;
        ALU_USER_INPUT  : in     vl_logic_vector(3 downto 0);
        BUS_SEL         : in     vl_logic_vector(2 downto 0);
        O_PC            : out    vl_logic_vector(4 downto 0);
        O_R0            : out    vl_logic_vector(3 downto 0);
        O_R1            : out    vl_logic_vector(3 downto 0);
        O_R2            : out    vl_logic_vector(3 downto 0);
        STACK_COUNT_UP  : in     vl_logic
    );
end \2020510030_Berkay-Dinc_Group19_Lab5_Project_BUS\;
