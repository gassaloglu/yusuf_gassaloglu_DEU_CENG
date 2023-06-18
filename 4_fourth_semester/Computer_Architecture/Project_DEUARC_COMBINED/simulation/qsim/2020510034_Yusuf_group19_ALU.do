onerror {quit -f}
vlib work
vlog -work work 2020510034_Yusuf_group19_ALU.vo
vlog -work work 2020510034_Yusuf_group19_ALU.vt
vsim -novopt -c -t 1ps -L cycloneiii_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.2021510008_Alperen_Aydin_Group19_Lab8_Project_CONTROL_UNIT_vlg_vec_tst
vcd file -direction 2020510034_Yusuf_group19_ALU.msim.vcd
vcd add -internal 2021510008_Alperen_Aydin_Group19_Lab8_Project_CONTROL_UNIT_vlg_vec_tst/*
vcd add -internal 2021510008_Alperen_Aydin_Group19_Lab8_Project_CONTROL_UNIT_vlg_vec_tst/i1/*
add wave /*
run -all
